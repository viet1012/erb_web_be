package com.api.erp_be.service.master;

import com.api.erp_be.mapper.master.SanPhamMapper;
import com.api.erp_be.model.SanPham;
import com.api.erp_be.repository.master.SanPhamRepository;
import com.api.erp_be.request.master.SanPhamRequest;
import com.api.erp_be.response.master.SanPhamResponse;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
@Transactional
public class SanPhamService {

    private final SanPhamRepository repository;
    private final SanPhamMapper mapper;

    public SanPhamService(SanPhamRepository repository, SanPhamMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // =========================
    // CRUD
    // =========================

    public Page<SanPhamResponse> getPage(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<SanPham> pageResult = repository.findAll(pageable);

        return pageResult.map(mapper::toResponse);
    }

    // Create
    public SanPhamResponse create(SanPhamRequest req) {
        SanPham sp = mapper.toEntity(req);

        // Lấy số lượng sản phẩm hiện có (kể cả đã delete, nếu muốn bỏ qua thì thêm điều kiện)
        long count = repository.count();

        // Tự động tạo mã SP1, SP2, ...
        String maTuDong = "SP" + (count + 1);
        sp.setMaSanPham(maTuDong);
        sp.setNgayTao(LocalDateTime.now());
        sp.setStatus("ACTIVE");

        repository.save(sp);
        return mapper.toResponse(sp);
    }


    // Read all
    public List<SanPhamResponse> getAll() {
        return repository.findByStatus("ACTIVE")
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    // Read by Id
    public SanPhamResponse getById(Integer id) {
        SanPham sp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với STT: " + id));
        return mapper.toResponse(sp);
    }

    // Update
    public SanPhamResponse update(Integer id, SanPhamRequest req) {
        SanPham sp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với STT: " + id));

        // Chỉ cập nhật nếu trường không null
        if (req.getTenSanPham() != null) {
            sp.setTenSanPham(req.getTenSanPham());
        }
        if (req.getNhomSanPham() != null) {
            sp.setNhomSanPham(req.getNhomSanPham());
        }
        if (req.getTrongLuong() != null) {
            sp.setTrongLuong(req.getTrongLuong());
        }
        if (req.getDonViTrongLuong() != null) {
            sp.setDonViTrongLuong(req.getDonViTrongLuong());
        }
        if (req.getSoLuongLenhSanXuat() != null) {
            sp.setSoLuongLenhSanXuat(req.getSoLuongLenhSanXuat());
        }

        sp.setNgayCapNhat(LocalDateTime.now());

        // Nên có kiểm tra null với nguoiCapNhat
        if (req.getNguoiCapNhat() != null) {
            sp.setNguoiCapNhat(req.getNguoiCapNhat());
        }

        repository.save(sp);
        return mapper.toResponse(sp);
    }

    // Delete
    public void delete(Integer id) {
        SanPham sp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với STT: " + id));

        sp.setStatus("DELETE");
        sp.setNgayCapNhat(LocalDateTime.now());

        repository.save(sp);
    }



    // =========================
    // Import Excel
    // =========================
    public List<SanPhamResponse> importFromExcel(MultipartFile file) {
        List<SanPhamResponse> importedList = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new RuntimeException("❌ File Excel không có sheet nào!");
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String maSanPham = getCellValue(row.getCell(1));
                if (maSanPham == null || maSanPham.isEmpty()) continue;

                if (repository.existsByMaSanPham(maSanPham)) {
                    System.out.println("⚠️ Bỏ qua: Mã sản phẩm đã tồn tại -> " + maSanPham);
                    continue;
                }

                SanPham sp = new SanPham();
                sp.setMaSanPham(maSanPham);
                sp.setTenSanPham(getCellValue(row.getCell(2)));
                sp.setNhomSanPham(getCellValue(row.getCell(3)));

                try {
                    sp.setTrongLuong(Double.parseDouble(getCellValue(row.getCell(4))));
                } catch (Exception e) {
                    sp.setTrongLuong(0.0);
                }

                sp.setDonViTrongLuong(getCellValue(row.getCell(5)));

                try {
                    sp.setSoLuongLenhSanXuat((int) parseDoubleSafe(getCellValue(row.getCell(10))));
                } catch (Exception e) {
                    sp.setSoLuongLenhSanXuat(0);
                }

                sp.setNgayTao(LocalDateTime.now());
                sp.setNguoiTao("import");

                repository.save(sp);
                importedList.add(mapper.toResponse(sp));
            }

            return importedList;

        } catch (IOException e) {
            throw new RuntimeException("❌ Lỗi khi đọc file Excel: " + e.getMessage());
        }
    }


    // =========================
    // Export Excel
    // =========================
    public ByteArrayInputStream exportToExcel() {
        List<SanPham> list = repository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("DanhSachSanPham");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            String[] headers = {
                    "STT", "Mã Sản Phẩm", "Tên Sản Phẩm", "Nhóm Sản Phẩm",
                    "Trọng Lượng", "Đơn Vị Trọng Lượng", "Ngày Tạo", "Người Tạo",
                    "Ngày Cập Nhật", "Người Cập Nhật", "SL Lệnh SX"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIdx = 1;
            for (SanPham sp : list) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(sp.getStt() != null ? sp.getStt() : 0);
                row.createCell(1).setCellValue(sp.getMaSanPham() != null ? sp.getMaSanPham() : "");
                row.createCell(2).setCellValue(sp.getTenSanPham() != null ? sp.getTenSanPham() : "");
                row.createCell(3).setCellValue(sp.getNhomSanPham() != null ? sp.getNhomSanPham() : "");
                row.createCell(4).setCellValue(sp.getTrongLuong() != null ? sp.getTrongLuong() : 0);
                row.createCell(5).setCellValue(sp.getDonViTrongLuong() != null ? sp.getDonViTrongLuong() : "");
                row.createCell(6).setCellValue(sp.getNgayTao() != null ? sp.getNgayTao().toString() : "");
                row.createCell(7).setCellValue(sp.getNguoiTao() != null ? sp.getNguoiTao() : "");
                row.createCell(8).setCellValue(sp.getNgayCapNhat() != null ? sp.getNgayCapNhat().toString() : "");
                row.createCell(9).setCellValue(sp.getNguoiCapNhat() != null ? sp.getNguoiCapNhat() : "");
                row.createCell(10).setCellValue(sp.getSoLuongLenhSanXuat() != null ? sp.getSoLuongLenhSanXuat() : 0);
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("❌ Lỗi khi tạo file Excel: " + e.getMessage());
        }
    }

    // =========================
    // Search nâng cao
    // =========================
    public List<SanPhamResponse> search(String keyword, String nhomSanPham, Double minWeight, Double maxWeight) {
        // Giả sử bạn có phương thức tìm kiếm trong repository theo các điều kiện
        // Nếu chưa có, bạn cần viết query tùy chỉnh trong repository hoặc sử dụng Specification/Criteria

        List<SanPham> list = repository.findAll(); // Thay bằng repo tìm kiếm thật

        return list.stream()
                .filter(sp -> (keyword == null || keyword.isEmpty() ||
                        sp.getMaSanPham().toLowerCase().contains(keyword.toLowerCase()) ||
                        sp.getTenSanPham().toLowerCase().contains(keyword.toLowerCase())))
                .filter(sp -> (nhomSanPham == null || nhomSanPham.isEmpty() ||
                        nhomSanPham.equalsIgnoreCase(sp.getNhomSanPham())))
                .filter(sp -> (minWeight == null || sp.getTrongLuong() >= minWeight))
                .filter(sp -> (maxWeight == null || sp.getTrongLuong() <= maxWeight))
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }


    // =========================
    // Thống kê (statistics)
    // =========================
    public Map<String, Object> getStatistics() {
        List<SanPham> list = repository.findAll();

        long total = list.size();
        Map<String, Long> byGroup = list.stream()
                .collect(Collectors.groupingBy(SanPham::getNhomSanPham, Collectors.counting()));

        double avgWeight = list.stream()
                .mapToDouble(sp -> sp.getTrongLuong() != null ? sp.getTrongLuong() : 0)
                .average()
                .orElse(0);

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("groupStats", byGroup);
        result.put("averageWeight", avgWeight);

        return result;
    }


    // =========================
    // Helper methods
    // =========================

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private double parseDoubleSafe(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }

}
