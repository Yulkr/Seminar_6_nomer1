package seminar6_nomer1;


import java.util.Stack;

class UndoableStringBuilder {

    private interface Action{
        void undo();
    }

    private class DeleteAction implements Action{
        private int size;

        public DeleteAction(int size) {
            this.size = size;
        }

        public void undo(){
            stringBuilder.delete(
                    stringBuilder.length() - size, stringBuilder.length());
        }
    }

    private StringBuilder stringBuilder;


    private Stack<Action> actions = new Stack<>();

    // конструктор
    public UndoableStringBuilder() {
        stringBuilder = new StringBuilder();
    }


    public UndoableStringBuilder reverse() {
        stringBuilder.reverse();

        Action action = new Action(){
            public void undo() {
                stringBuilder.reverse();
            }
        };

        actions.add(action);

        return this;
    }


    public UndoableStringBuilder append(String str) {
        stringBuilder.append(str);

        Action action = new Action(){
            public void undo() {
                stringBuilder.delete(
                        stringBuilder.length() - str.length() -1,
                        stringBuilder.length());
            }
        };

        actions.add(action);
        return this;
    }


    public UndoableStringBuilder appendCodePoint(int codePoint) {
        int lenghtBefore = stringBuilder.length();
        stringBuilder.appendCodePoint(codePoint);
        actions.add(new DeleteAction(stringBuilder.length() - lenghtBefore));
        return this;
    }

    public UndoableStringBuilder delete(int start, int end) {
        String deleted = stringBuilder.substring(start, end);
        stringBuilder.delete(start, end);
        actions.add(() -> stringBuilder.insert(start, deleted));
        return this;
    }



    public void undo(){
        if(!actions.isEmpty()){
            actions.pop().undo();
        }
    }

    public String toString() {
        return stringBuilder.toString();
    }
}