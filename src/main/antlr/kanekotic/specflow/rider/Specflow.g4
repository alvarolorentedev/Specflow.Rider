grammar Specflow;

@header {
    package kanekotic.specflow.rider;
}

file returns [List<String> values]
    @init { $values = new ArrayList<String>(); }
    : NEWLINE;

NEWLINE :   '\r'? '\n';
