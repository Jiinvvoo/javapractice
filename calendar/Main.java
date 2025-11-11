package calendar;

public class Main {
	   
    public static void main(String[] args) {
        MemoManager memoManager = new MemoManager();
        memoManager.loadMemos();
        new CalendarView(memoManager);
    }
}

