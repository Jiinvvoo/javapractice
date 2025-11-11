package calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.util.*;

//JFrame을 상속받가 창을 만드는 클래스
public class CalendarView extends JFrame {
    private YearMonth currentMonth; //현재 표시중인 달
    private JPanel calendarPanel; //실제 달력 날짜 버튼들이 들어가는 패널
    private JLabel monthLabel; //2025년 11월 << 이런식으로 표시하는 라벨
    private MemoManager memoManager; // 메모 데이터를 관리(저장/불러오기/검색)하는 클래스 객체

    //MemoManager 객체를 받아서 내부에서 사용함
    public CalendarView(MemoManager memoManager) {
        this.memoManager = memoManager;
        this.currentMonth = YearMonth.now(); //현재 달을 기준으로 달력을 처음 표시

        setTitle("달력 메모장");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        setVisible(true);
    }

    // 화면 구성
    private void createUI() {
       
       // 위쪽 영역에 월 이동 버튼 + 검색창 배치
        JPanel topPanel = new JPanel(new BorderLayout());

        // 월 이동 버튼
        JPanel monthPanel = new JPanel();
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        
        // 현재 표시중인 년,월을 가운데 정렬로 표시
        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));

        prevButton.addActionListener(e -> {
            currentMonth = currentMonth.minusMonths(1);
            refreshCalendar();
        });
        nextButton.addActionListener(e -> {
            currentMonth = currentMonth.plusMonths(1);
            refreshCalendar();
        });

        monthPanel.add(prevButton);
        monthPanel.add(monthLabel);
        monthPanel.add(nextButton);

        // 검색창
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("검색");
        // 검색 버튼 클릭시 showSearchResults(keyword) 실행
        
        searchPanel.add(new JLabel("메모 검색:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty()) {
                showSearchResults(keyword);
            }
        });

        topPanel.add(monthPanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);

        // 달력 본체
        calendarPanel = new JPanel(new GridLayout(0, 7, 5, 5));
        // GridLayout으로 7열 달력을 만듦
        add(topPanel, BorderLayout.NORTH);
        add(calendarPanel, BorderLayout.CENTER);

        refreshCalendar();

        // 윈도우 종료시 자동으로 메모를 파일로 저장
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                memoManager.saveMemos();
            }
        });
    }
    
    private void refreshCalendar() {
        calendarPanel.removeAll(); // 이전 달력 지우기
        monthLabel.setText(currentMonth.getYear() + "년 " + currentMonth.getMonthValue() + "월");

        String[] days = {"일", "월", "화", "수", "목", "금", "토"};
        for (String day : days) {
            JLabel lbl = new JLabel(day, SwingConstants.CENTER);
            lbl.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            calendarPanel.add(lbl);
        }

        LocalDate firstDay = currentMonth.atDay(1);
        int startDay = firstDay.getDayOfWeek().getValue() % 7;
        for (int i = 0; i < startDay; i++) calendarPanel.add(new JLabel(""));

        for (int day = 1; day <= currentMonth.lengthOfMonth(); day++) {
            LocalDate date = currentMonth.atDay(day);
            JButton btn = new JButton(String.valueOf(day));

            if (memoManager.getMemoMap().containsKey(date)) {
                btn.setBackground(new Color(255, 250, 200));
            }

            btn.addActionListener(e -> openMemoDialog(date, btn));
            calendarPanel.add(btn);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void openMemoDialog(LocalDate date, JButton button) {
        JDialog dialog = new JDialog(this, date + " 메모", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textArea.setText(memoManager.getMemoMap().getOrDefault(date, ""));
        JScrollPane scrollPane = new JScrollPane(textArea);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton saveBtn = new JButton("저장");
        JButton deleteBtn = new JButton("삭제");
        JButton closeBtn = new JButton("닫기");

        saveBtn.addActionListener(e -> {
            memoManager.addMemo(date, textArea.getText());
            button.setBackground(new Color(255, 250, 200));
            memoManager.saveMemos();
            dialog.dispose();
        });

        deleteBtn.addActionListener(e -> {
            memoManager.addMemo(date, "");
            button.setBackground(null);
            memoManager.saveMemos();
            dialog.dispose();
        });

        closeBtn.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(closeBtn);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showSearchResults(String keyword) {
        java.util.List<LocalDate> results = memoManager.searchMemos(keyword);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "검색 결과가 없습니다.");
            return;
        }

        JDialog dialog = new JDialog(this, "검색 결과", true);
        dialog.setSize(300, 300);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(this);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (LocalDate date : results) {
            String memo = memoManager.getMemoMap().get(date).split("\n")[0];
            listModel.addElement(date + " : " + memo);
        }

        JList<String> list = new JList<>(listModel);
        dialog.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton openBtn = new JButton("선택한 날짜 열기");
        openBtn.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index != -1) {
                LocalDate date = results.get(index);
                dialog.dispose();
                currentMonth = YearMonth.of(date.getYear(), date.getMonthValue());
                refreshCalendar();
                openMemoDialog(date, new JButton());
            }
        });

        dialog.add(openBtn, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}