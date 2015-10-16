package DBObjects;

import java.util.Objects;

/**
 * This represents a possible answer to a <code>Problem</code> object. A correct
 * answer means that this answer is the answer a quiz-taker must guess in order
 * to get the problem right.
 * @author Armando
 */
public class Answer {
    private String answer;
    private boolean isCorrect;

    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }
    
    /** @return The correctness value of this <code>Answer</code>. **/
    public boolean isCorrect() {
        return isCorrect;
    }

    /** Re-classifies this <code>Answer</code> with the specified correctness value.
      * @param isCorrect The new correctness value to assign to this Answer. **/
    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
    
    public void setAnswer(String newAnswer) {
        if (newAnswer != null && newAnswer.trim().length() > 0)
            this.answer = newAnswer;
    }
    
    public String getAnswer() {
        return this.answer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        final Answer other = (Answer) obj;
        return Objects.equals(this.answer, other.answer);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.answer);
        return hash;
    }
    
    @Override
    public String toString() {
        return getAnswer();
    }
    
}
