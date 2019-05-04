package pxgd.hyena.com.geoquiz;

public class Question {

    private int resId;
    private boolean answer;

    public Question(int resId, boolean answer) {
        this.resId = resId;
        this.answer = answer;
    }
    public int getResId() {
        return resId;
    }
    public void setResId(int resId) {
        this.resId = resId;
    }
    public boolean isAnswer() {
        return answer;
    }
    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
