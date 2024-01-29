public class MyClass {
    interface TextAnalyzer {
        Label processText(String text);
    }

    enum Label {
        SPAM, NEGATIVE_TEXT, TOO_LONG, OK
    }

    public Label checkLabels(TextAnalyzer[] analyzers, String text) {
        for (int i = 0; i < analyzers.length; i++) {
            Label k = analyzers[i].processText(text);
            if (k != Label.OK) {
                return k;
            }
        }
        return Label.OK;
    }

    abstract class KeywordAnalyzer implements TextAnalyzer {
        protected abstract String[] getKeywords();

        protected abstract Label getLabel();

        public Label processText(String text) {
            for (int i = 0; i < getKeywords().length; i++) {
                if (text.contains(getKeywords()[i])) {
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

        protected Label getLabel() {
            return Label.SPAM;
        }

    }

    class NegativeTextAnalyzer extends KeywordAnalyzer {
        final String[] STRING = {":(", "=(", ":|"};

        protected String[] getKeywords() {
            return STRING;
        }

        protected Label getLabel() {
            return Label.NEGATIVE_TEXT;
        }
    }

    class TooLongTextAnalyzer implements TextAnalyzer {
        private int maxLength;

        public TooLongTextAnalyzer(int maxLength) {
            this.maxLength = maxLength;
        }

        protected Label getLabel() {
            return Label.TOO_LONG;
        }

        public Label processText(String text) {
            if (text.length() > this.maxLength) {
                return getLabel();
            }
            return Label.OK;
        }

    }
}
