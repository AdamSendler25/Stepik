public class MyClass {
    interface TextAnalyzer {
        Label processText(String text);
    }
    enum Label {
        SPAM, NEGATIVE_TEXT, TOO_LONG, OK
    }
    public Label checkLabels(TextAnalyzer[] analyzers, String text) {
        for(int i = 0; i < analyzers.length; i++) {
            if(analyzers[i].processText(text) != Label.OK) {
                return analyzers[i].processText(text);
            }
        }
        return Label.OK;
    }

    abstract class KeywordAnalyzer implements TextAnalyzer {
        protected abstract String[] getKeywords();
        protected abstract Label getLabel();
        public Label processText(String text) {
            for(int i = 0; i < getKeywords().length; i++) {
                if(text.contains(getKeywords()[i])) {
                    return getLabel();
                }
            }

            return Label.OK;
        }


    }
    class SpamAnalyzer extends KeywordAnalyzer {
        private String[] keywords;
        SpamAnalyzer(String[] keywords) {
            this.keywords = keywords;
        }
        protected String[] getKeywords() {
            return this.keywords;
        }
        protected Label getLabel(){
            return Label.SPAM;
        }

    }

    class NegativeTextAnalyzer extends KeywordAnalyzer {
        protected String[] getKeywords() {
            String[] string = {":(", "=(", ":|"};
            return string;
        }
        protected Label getLabel() {
            return Label.NEGATIVE_TEXT;
        }
    }

    class TooLongTextAnalyzer implements TextAnalyzer{
        private int maxLength;
        public TooLongTextAnalyzer(int maxLength) {
            this.maxLength = maxLength;
        }
        protected Label getLabel() {
            return Label.TOO_LONG;
        }
        public Label processText(String text) {
            if(text.length() > this.maxLength) {
                return getLabel();
            }
            return Label.OK;
        }

    }

}
