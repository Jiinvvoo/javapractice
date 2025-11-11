package calendar;

import java.time.LocalDate;
import java.io.*;
import java.util.*;

public class MemoManager {
	// 메모 저장 파일 이름
    private static final String SAVE_FILE = "memoData.txt";
    
    // 날짜별 메모를 저장하는 해시맵 
    private HashMap<LocalDate, String> memoMap = new HashMap<>();

    // 외부에서 메모 목록에 접근할 수 있도록 getter 제공
    public HashMap<LocalDate, String> getMemoMap() {
        return memoMap;
    }

    // 메모 추가
    public void addMemo(LocalDate date, String memo) {
        if (memo != null && !memo.trim().isEmpty()) {
            memoMap.put(date, memo); // 특정 날짜에 새 메모 추가 또는 기존 메모 수정
        } else {
            memoMap.remove(date); // 빈 메모는 삭제 처리
        }
    }

    // 메모 검색
    public List<LocalDate> searchMemos(String keyword) {
        List<LocalDate> result = new ArrayList<>();
        for (Map.Entry<LocalDate, String> entry : memoMap.entrySet()) {
        	// 각 날짜의 메모 내용이 검색어를 포함하는지 확인
            if (entry.getValue().contains(keyword)) {
                result.add(entry.getKey()); // 포함 시 날짜 추가
            }
        }
        return result;
    }

    // 메모 파일 저장
    public void saveMemos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            for (Map.Entry<LocalDate, String> entry : memoMap.entrySet()) {
                writer.write(entry.getKey().toString()); // 날짜 저장
                writer.newLine();
                writer.write(entry.getValue().replace("\n", "\\n")); // 줄바꿈 문자를 \n 형태로 반환
                writer.newLine();
                writer.write("---"); // 메모간 구분을 위한 선 추가
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("메모 저장 오류: " + e.getMessage());
        }
    }

    // 메모 파일 불러오기
    public void loadMemos() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) return; // 파일이 없으면 종료

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            LocalDate date = null;
            StringBuilder memoBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.equals("---")) { //한 메모의 끝
                    if (date != null) {
                    	// "\n"을 실제 줄바꿈으로 복원 후 HashMap에 저장
                        memoMap.put(date, memoBuilder.toString().replace("\\n", "\n"));
                        memoBuilder.setLength(0); // 메모 내용 초기화
                    }
                    date = null; // 다음 메모를 위한 초기화
                } else if (date == null) {
                    date = LocalDate.parse(line); // 첫 줄은 날짜 → LocalDate로 변환
                } else {
                    memoBuilder.append(line); // 날짜 다음 줄부터는 메모 내용
                }
            }
        } catch (IOException e) {
            System.out.println("메모 불러오기 오류: " + e.getMessage());
        }
    }
}

