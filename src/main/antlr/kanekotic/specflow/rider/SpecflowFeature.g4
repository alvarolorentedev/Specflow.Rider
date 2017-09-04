grammar SpecflowFeature;

import SpecflowScenario;

@header {
    package kanekotic.specflow.rider;
}

file returns [List<String> values]
    @init { $values = new ArrayList<String>(); }
    : 'Feature: ' EOF;

