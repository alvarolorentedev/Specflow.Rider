grammar SpecflowFeature;

import SpecflowScenario;

feature returns [List<String> values]
    @init { $values = new ArrayList<String>(); }
    : 'Feature: ';

