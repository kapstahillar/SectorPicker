package com.kapsta.sectorpicker.unit;


import com.kapsta.sectorpicker.util.StringUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilitiesTest {

    @Test
    public void Add_2_indents_to_string() {
        String baseString = "This is a string";
        String finalString = StringUtil.addNbspToString(baseString, 2);
        assertEquals("        This is a string", finalString);
    }

    @Test
    public void Replace_known_character_from_string() {
        String baseString = "This is a string &nbsp; â€™";
        String finalString = StringUtil.replaceKnownCharactersInString(baseString);
        assertEquals("This is a string   '", finalString);
    }
}
