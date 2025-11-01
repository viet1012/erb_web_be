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
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SanPhamService {

    private final SanPhamRepository repository;
    private final SanPhamMapper mapper;

    public SanPhamService(SanPhamRepository repository, SanPhamMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // 🔹 Lấy tất cả sản phẩm
    public List<SanPhamResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    // 🔹 Lấy theo ID
    public SanPhamResponse getById(Integer id) {
        SanPham sp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với STT: " + id));
        return mapper.toResponse(sp);
    }

    // 🔹 Tạo mới
    public SanPhamResponse create(SanPhamRequest req) {
        if (repository.existsByMaSanPham(req.getMaSanPham())) {
            throw new RuntimeException("❌ Mã sản phẩm đã tồn tại!");
        }
        SanPham sp = mapper.toEntity(req);
        sp.setNgayTao(LocalDateTime.now());
        sp.setNguoiTao(req.getNguoiTao());
        repository.save(sp);
        return mapper.toResponse(sp);
    }

    // 🔹 Cập nhật
    public SanPhamResponse update(Integer id, SanPhamRequest req) {
        SanPham sp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với STT: " + id));

        sp.setTenSanPham(req.getTenSanPham());
        sp.setNhomSanPham(req.getNhomSanPham());
        sp.setTrongLuong(req.getTrongLuong());
        sp.setDonViTrongLuong(req.getDonViTrongLuong());
        sp.setSoLuongLenhSanXuat(req.getSoLuongLenhSanXuat());
        sp.setNgayCapNhat(LocalDateTime.now());
        sp.setNguoiCapNhat(req.getNguoiCapNhat());

        repository.save(sp);
        return mapper.toResponse(sp);
    }

    // 🔹 Xóa
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Không tồn tại sản phẩm để xóa!");
        }
        repository.deleteById(id);
    }

    // ============================================================
    // 🔸 [NEW] /api/san-pham/import — Import danh sách sản phẩm từ file Excel
    // ============================================================
    public List<SanPhamResponse> importFromExcel(MultipartFile file) {
        List<SanPhamResponse> importedList = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new RuntimeException("❌ File Excel không có sheet nào!");
            }

            // 👉 Bắt đầu đọc từ dòng 1 (bỏ qua header)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String maSanPham = getCellValue(row.getCell(1));
                if (maSanPham == null || maSanPham.isEmpty()) continue; // bỏ dòng trống

                // ⚠️ Kiểm tra trùng mã sản phẩm
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
                sp.setSoLuongLenhSanXuat((int) parseDoubleSafe(getCellValue(row.getCell(10))));
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

    // ============================================================
    // 🔸 Hàm phụ trợ đọc giá trị ô Excel an toàn
    // ============================================================
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

    // ============================================================
    // 🔸 /api/san-pham/export — Xuất danh sách sản phẩm ra Excel
    // ============================================================
    public ByteArrayInputStream exportToExcel() {
        List<SanPham> list = repository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("DanhSachSanPham");

            // 👉 1. Tạo style header
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            // 👉 2. Header row
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

            // 👉 3. Ghi dữ liệu sản phẩm
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

            // 👉 4. Auto-size cột
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("❌ Lỗi khi tạo file Excel: " + e.getMessage());
        }
    }

    // ============================================================
    // 🔸 [2] /api/san-pham/statistics — Thống kê sản phẩm
    // ============================================================
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

    // ============================================================
    // 🔸 [3] /api/don-gia/import — Import file Excel đơn giá (placeholder)
    // ============================================================
    public void importDonGia(MultipartFile file) {
        // TODO: parse Excel -> save DonGia entities
        // validate MaSanPham có tồn tại không
    }

    // ============================================================
    // 🔸 [4] /api/don-gia/active — Lấy đơn giá hiện hành
    // ============================================================
    // (API này sẽ nằm ở DonGiaService, nhưng có thể bạn sẽ gọi từ SanPhamService)
    // gợi ý logic: repository.findByMaSanPhamAndEffectiveDateBefore(now)

    // ============================================================
    // 🔸 [5] /api/don-gia/lich-su — Lịch sử thay đổi giá
    // ============================================================
    // (Cũng nên nằm ở DonGiaService)
    // Gợi ý: tạo bảng DON_GIA_HISTORY để ghi lại mỗi lần thay đổi đơn giá.
}
