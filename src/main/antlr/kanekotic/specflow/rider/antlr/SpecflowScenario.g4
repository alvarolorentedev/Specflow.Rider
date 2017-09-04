grammar SpecflowScenario;

scenario returns [List<String> values]
    @init { $values = new ArrayList<String>(); }
    : 'Scenario: ';