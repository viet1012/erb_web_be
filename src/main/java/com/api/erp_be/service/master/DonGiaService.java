package com.api.erp_be.service.master;

import com.api.erp_be.mapper.master.DonGiaMapper;
import com.api.erp_be.model.DonGia;
import com.api.erp_be.model.SanPham;
import com.api.erp_be.repository.master.DonGiaRepository;
import com.api.erp_be.repository.master.SanPhamRepository;
import com.api.erp_be.request.master.DonGiaRequest;
import com.api.erp_be.response.master.DonGiaResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DonGiaService {

    private final SanPhamRepository sanPhamRepository;
    private final DonGiaRepository donGiaRepository;
    private final DonGiaMapper donGiaMapper;

    public DonGiaService(DonGiaRepository donGiaRepository,
                         DonGiaMapper donGiaMapper,
                         SanPhamRepository sanPhamRepository) {
        this.donGiaRepository = donGiaRepository;
        this.donGiaMapper = donGiaMapper;
        this.sanPhamRepository = sanPhamRepository;
    }

    // ============================================================
    // 🔸 [1] Lấy toàn bộ danh sách đơn giá
    // ============================================================
    public List<DonGiaResponse> getAll() {
        return donGiaRepository.findAll()
                .stream()
                .map(donGiaMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // 🔸 [2] Lấy đơn giá theo ID
    // ============================================================
    public DonGiaResponse getById(Integer id) {
        DonGia entity = donGiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn giá có STT = " + id));
        return donGiaMapper.toResponse(entity);
    }

    // ============================================================
    // 🔸 [3] Thêm mới đơn giá
    // ============================================================
    public DonGiaResponse create(DonGiaRequest req) {
        if (!sanPhamRepository.existsByMaSanPham(req.getMaSanPham())) {
            throw new RuntimeException("❌ Mã sản phẩm '" + req.getMaSanPham() + "' không tồn tại!");
        }

        DonGia entity = donGiaMapper.toEntity(req);
        entity.setNgayTao(LocalDateTime.now());
        entity.setNguoiTao("system");

        DonGia saved = donGiaRepository.save(entity);
        return donGiaMapper.toResponse(saved);
    }

    // ============================================================
    // 🔸 [4] Cập nhật đơn giá
    // ============================================================
    public DonGiaResponse update(Integer id, DonGiaRequest req) {
        DonGia entity = donGiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn giá có STT = " + id));

        if (!sanPhamRepository.existsByMaSanPham(req.getMaSanPham())) {
            throw new RuntimeException("❌ Mã sản phẩm '" + req.getMaSanPham() + "' không tồn tại!");
        }

        entity.setMaSanPham(req.getMaSanPham());
        entity.setMaKhachHang(req.getMaKhachHang());
        entity.setDonGia(req.getDonGia());
        entity.setDonViSuDung(req.getDonViSuDung());
        entity.setNgayCapNhat(LocalDateTime.now());
        entity.setNguoiCapNhat("system");

        DonGia updated = donGiaRepository.save(entity);
        return donGiaMapper.toResponse(updated);
    }

    // ============================================================
    // 🔸 [5] Xóa đơn giá
    // ============================================================
    public void delete(Integer id) {
        if (!donGiaRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy đơn giá có STT = " + id);
        }
        donGiaRepository.deleteById(id);
    }

    // ============================================================
    // 🔸 [6] /api/don-gia/import — Import file Excel đơn giá
    // ============================================================
    public List<DonGiaResponse> importDonGia(MultipartFile file) {
        List<DonGiaResponse> importedList = new ArrayList<>();

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) throw new RuntimeException("❌ File Excel không có sheet!");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String maSanPham = getCellValue(row.getCell(0));
                String maKhachHang = getCellValue(row.getCell(1));
                String donGiaStr = getCellValue(row.getCell(2));
                Double donViSuDung = Double.valueOf(getCellValue(row.getCell(3)));

                if (maSanPham.isEmpty()) continue;
                if (!sanPhamRepository.existsByMaSanPham(maSanPham)) {
                    System.out.println("⚠️ Bỏ qua: Mã sản phẩm không tồn tại -> " + maSanPham);
                    continue;
                }

                DonGia entity = new DonGia();
                entity.setMaSanPham(maSanPham);
                entity.setMaKhachHang(maKhachHang);
                entity.setDonGia(Double.parseDouble(donGiaStr.isEmpty() ? "0" : donGiaStr));
                entity.setDonViSuDung(donViSuDung);
                entity.setNgayTao(LocalDateTime.now());
                entity.setNguoiTao("import");

                donGiaRepository.save(entity);
                importedList.add(donGiaMapper.toResponse(entity));
            }

            return importedList;

        } catch (Exception e) {
            throw new RuntimeException("❌ Lỗi import file Excel: " + e.getMessage());
        }
    }

    // ============================================================
    // 🔸 [7] /api/don-gia/active — Lấy đơn giá hiện hành
    // ============================================================
    public DonGiaResponse getActivePrice(String maSanPham) {
        // Giả định đơn giá hiện hành là dòng có ngày tạo mới nhất
        DonGia latest = donGiaRepository.findTopByMaSanPhamOrderByNgayTaoDesc(maSanPham)
                .orElseThrow(() -> new RuntimeException("Không có đơn giá hiện hành cho mã sản phẩm: " + maSanPham));
        return donGiaMapper.toResponse(latest);
    }

    // ============================================================
    // 🔸 [8] /api/don-gia/lich-su — Lịch sử thay đổi giá
    // ============================================================
    public List<DonGiaResponse> getHistory(String maSanPham) {
        List<DonGia> list = donGiaRepository.findByMaSanPhamOrderByNgayCapNhatDesc(maSanPham);
        return list.stream().map(donGiaMapper::toResponse).collect(Collectors.toList());
    }

    // ============================================================
    // 🔹 Helper: đọc ô Excel
    // ============================================================
    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue().trim();
            case NUMERIC: return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            default: return "";
        }
    }
}
