grammar SpecflowFeature;

import SpecflowScenario;

file returns [List<String> values]
    @init { $values = new ArrayList<String>(); }
    : 'Feature: ' EOF;

