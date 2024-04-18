package com._S2JG.service;

import com._S2JG.model.dao.BusDao;
import com._S2JG.model.dao.ReservationDao;
import com._S2JG.model.dao.RouteDao;
import com._S2JG.model.dao.RouteTimeDao;
import com._S2JG.model.dto.BusDto;
import com._S2JG.model.dto.ReservationDto;
import com._S2JG.model.dto.RouteDto;
import com._S2JG.model.dto.RouteTimeDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    @Autowired
    private BusDao busDao;
    @Autowired
    private RouteDao routeDao;
    @Autowired
    private RouteTimeDao routeTimeDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private ReservationDao reservationDao;

    public List< Map<String,Object> > doUploadFile(long memberNo, MultipartFile file, int stand){
        System.out.println("FileService.doUploadBus 실행");

        List< Map<String,Object> > list = readExcel(file,stand);

        return list;
    }


    public boolean doDownloadExel(long memberNo, int stand){
        System.out.println("FileService.doDownloadBus 실행");
        List<Object> list = new ArrayList<>();
        if(stand == 1){
            list = busDao.doDownloadBus(memberNo);
        } else if(stand == 2) {
            list = routeDao.doGetAllRoute();
        } else if(stand == 3) {
            list = routeTimeDao.doGetAllRouteTime();
        } else if(stand == 4) {
            list = reservationDao.dogetAdminReservation();
        }


        System.out.println("list = " + list);
        return writeExcelFile(list,stand);
    }

    // 엑셀 파일 읽기
    public List<Map< String , Object >> readExcel(MultipartFile file , int stand){
        System.out.println("FileService.readExcel 실행");
        List< Map<String,Object> > list = new ArrayList<>();

        try{
            // XSSFWorkbook 파일 열기
            InputStream is = file.getInputStream();
            // 해당파일 XSSFWorkbook로 받기
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            // 0번째 시트 열기
            XSSFSheet sheet = workbook.getSheetAt(0);
            // 0번째줄 읽기
//            XSSFRow row = sheet.getRow(0);
            // 0번째줄 1번째행을 String형으로 변환
//            row.getCell(3).setCellType(CellType.STRING);
            // 0번째줄 1번째행의 값을 String으로 가져옴 숫자는 NUMBERIC 날짜는 Date

            int i = 0;
            // 양식이 정해져있다고 약속
            // 1.busNumber 2.busClassNo 3.busPosition
            while(true) {
                Map<String,Object> map = new HashMap<>();

                if (sheet.getRow(i+1) == null) break;

                XSSFRow row = sheet.getRow(i+1); // 두번째줄부터
                if(stand == 1){
                    map.put("busNumber", row.getCell(0).getStringCellValue()); // 2째줄 1번째값
                    map.put("busClassNo", (int)row.getCell(1).getNumericCellValue()); // 2째줄 2번째값
                    map.put("busPosition", row.getCell(2).getStringCellValue()); // 2째줄 3번째값
                } else if(stand == 2){

                    map.put("routeStartTerminal", row.getCell(0).getStringCellValue()); // 2째줄 1번째값
                    map.put("routeEndTerminal",  row.getCell(1).getStringCellValue()); // 2째줄 2번째값
                    map.put("routePrice", (int) row.getCell(2).getNumericCellValue()); // 2째줄 3번째값
                } else if(stand == 3){
                    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String routeTimeStartTime = transFormat.format(row.getCell(0).getDateCellValue());
                    String routeTimeEndTime = transFormat.format(row.getCell(1).getDateCellValue());
                    map.put("routeTimeStartTime", routeTimeStartTime); // 2째줄 1번째값
                    map.put("routeTimeEndTime", routeTimeEndTime ); // 2째줄 2번째값
                    map.put("busNo", (int) row.getCell(2).getNumericCellValue()); // 2째줄 3번째값
                    map.put("routeNo", (int) row.getCell(3).getNumericCellValue()); // 2째줄 4번째값
                }
                list.add(map);
                i++;
            }
            // ====================================================
        }catch (Exception e){
            System.out.println("파일 읽기 오류 :" + e);
        }
        return list;
    }

    // 엑셀 파일 쓰기
    public boolean writeExcelFile(List<Object> list, int stand){
            // Workbook 생성
            Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상

            // Sheet 생성
            Sheet sheet1 = xlsxWb.createSheet("Sheet1");

            Row row = null;

            String fileTB = "";

            if(stand == 1){
                fileTB = "Bus";
                // 첫 번째 줄
                row = sheet1.createRow(0);
                row.createCell(0).setCellValue(" ");
                row.createCell(1).setCellValue("차량번호");
                row.createCell(2).setCellValue("차량등급");
                row.createCell(3).setCellValue("차량위치");
                row.createCell(4).setCellValue("등록일자");

                // 두번째줄부터 ~
                for(int i=0; i<list.size(); i++){
                    row = sheet1.createRow(i+1);
                    row.createCell(0).setCellValue(i+1);
                    row.createCell(1).setCellValue(((BusDto)list.get(i)).getBusNumber());
                    row.createCell(2).setCellValue(((BusDto)list.get(i)).getBusClassNo());
                    row.createCell(3).setCellValue(((BusDto)list.get(i)).getBusPosition());
                    row.createCell(4).setCellValue(((BusDto)list.get(i)).getBusDate());
                }
            } else if(stand == 2){
                fileTB = "Route";
                // 첫 번째 줄
                row = sheet1.createRow(0);
                row.createCell(0).setCellValue(" ");
                row.createCell(1).setCellValue("출발 터미널");
                row.createCell(2).setCellValue("도착 터미널");
                row.createCell(3).setCellValue("가격");
                row.createCell(4).setCellValue("등록일자");

                // 두번째줄부터 ~
                int i=0;
                for(Object dto : list){
                    row = sheet1.createRow(i+1);
                    row.createCell(0).setCellValue(i+1);
                    row.createCell(1).setCellValue(((RouteDto)dto).getRouteStartTerminal());
                    row.createCell(2).setCellValue(((RouteDto)dto).getRouteEndTerminal());
                    row.createCell(3).setCellValue(((RouteDto)dto).getRoutePrice());
                    row.createCell(4).setCellValue(((RouteDto)dto).getRouteDate());
                    i++;
                };

            } else if(stand == 3){
                fileTB = "RouteTime";
                // 첫 번째 줄
                row = sheet1.createRow(0);
                row.createCell(0).setCellValue(" ");
                row.createCell(1).setCellValue("출발 시간");
                row.createCell(2).setCellValue("도착 시간");
                row.createCell(3).setCellValue("버스");
                row.createCell(4).setCellValue("노선");
                row.createCell(5).setCellValue("등록일자");

                // 두번째줄부터 ~
                int i=0;
                for(Object dto : list){
                    System.out.println("dto = " + dto);
                    row = sheet1.createRow(i+1);
                    row.createCell(0).setCellValue(i+1);
                    row.createCell(1).setCellValue(((RouteTimeDto)dto).getRouteTimeStartTime());
                    row.createCell(2).setCellValue(((RouteTimeDto)dto).getRouteTimeEndTime());
                    row.createCell(3).setCellValue(((RouteTimeDto)dto).getBusNo());
                    row.createCell(4).setCellValue(((RouteTimeDto)dto).getRouteNo());
                    row.createCell(5).setCellValue(((RouteTimeDto)dto).getRouteTimeDate());
                    i++;
                };

            } else if(stand == 4){
                fileTB = "Reservation";
                // 첫 번째 줄
                row = sheet1.createRow(0);
                row.createCell(0).setCellValue(" ");
                row.createCell(1).setCellValue("예약자 이름");
                row.createCell(2).setCellValue("출발지");
                row.createCell(3).setCellValue("출발 시간");
                row.createCell(4).setCellValue("도착지");
                row.createCell(5).setCellValue("도착 시간");
                row.createCell(6).setCellValue("시트번호");
                row.createCell(7).setCellValue("탑승확인");
                row.createCell(8).setCellValue("가격");
                row.createCell(9).setCellValue("예약일");

                // 두번째줄부터 ~
                int i=0;
                for(Object dto : list){
                    System.out.println("dto = " + dto);
                    row = sheet1.createRow(i+1);
                    row.createCell(0).setCellValue(i+1);
                    row.createCell(1).setCellValue(((ReservationDto)dto).getMemberName());
                    row.createCell(2).setCellValue(((ReservationDto)dto).getRouteStartTerminal());
                    row.createCell(3).setCellValue(((ReservationDto)dto).getRouteTimeStartTime());
                    row.createCell(4).setCellValue(((ReservationDto)dto).getRouteEndTerminal());
                    row.createCell(5).setCellValue(((ReservationDto)dto).getRouteTimeEndTime());
                    row.createCell(6).setCellValue(((ReservationDto)dto).getReservationSeatNum());
                    row.createCell(7).setCellValue(((ReservationDto)dto).getReservationStatus());
                    row.createCell(8).setCellValue(((ReservationDto)dto).getReservationPrice());
                    row.createCell(9).setCellValue(((ReservationDto)dto).getReservationDate());
                    i++;
                };

            }



        // excel 파일 저장
        try {
            String downloadPath = "C:\\Users\\504\\Downloads\\";
            String fileName = fileTB+"SSJGDownloadExel.xlsx";
            response.setHeader("Content-Disposition","attachment;filename="+ fileName);
            File xlsFile = new File(downloadPath+fileName);
            FileOutputStream fileOut = new FileOutputStream(xlsFile);
            xlsxWb.write(fileOut);
            return true;
        } catch (Exception e) {
            System.out.println("파일 다운로드 오류 : " + e);
        }

        return false;
    }
}
