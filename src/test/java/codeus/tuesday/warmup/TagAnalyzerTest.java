package codeus.tuesday.warmup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TagAnalyzerTest {

    private TagAnalyzer analyzer = new TagAnalyzer();

    @Test
    @DisplayName("Should validate properly nested tags")
    void shouldValidateProperlyNestedTags() {
        assertTrue(analyzer.isValid("<a><b></b></a>"));
        assertTrue(analyzer.isValid("<div><span></span></div>"));
    }

    @Test
    @DisplayName("Should fail for improperly nested tags")
    void shouldFailForImproperNesting() {
        assertFalse(analyzer.isValid("<a><b></a></b>"));
        assertFalse(analyzer.isValid("<div><span></div></span>"));
    }

    @Test
    @DisplayName("Should validate multiple valid tags")
    void shouldValidateMultipleValidTags() {
        assertTrue(analyzer.isValid("<a></a><b></b>"));
        assertTrue(analyzer.isValid("<div></div><span></span>"));
    }

    @Test
    @DisplayName("Should fail for unclosed tags")
    void shouldFailForUnclosedTags() {
        assertFalse(analyzer.isValid("<a><b>"));
        assertFalse(analyzer.isValid("<div>"));
    }

    @Test
    @DisplayName("Should validate text without tags")
    void shouldValidateTextWithoutTags() {
        assertTrue(analyzer.isValid("just some text"));
        assertTrue(analyzer.isValid(""));
    }

    @Test
    @DisplayName("Should fail for invalid tag names")
    void shouldFailForInvalidTagNames() {
        assertFalse(analyzer.isValid("<1a></1a>"));
        assertFalse(analyzer.isValid("<@div></@div>"));
        assertFalse(analyzer.isValid("<div$></div$>"));
    }

    @Test
    @DisplayName("Should handle complex nested structures")
    void shouldHandleComplexNestedStructures() {
        assertTrue(analyzer.isValid("<a><b><c></c></b></a>"));
        assertTrue(analyzer.isValid("<div><span><p></p></span></div>"));
    }

    @Test
    @DisplayName("Should fail for malformed tags")
    void shouldFailForMalformedTags() {
        assertFalse(analyzer.isValid("<a<b></b></a>"));
        assertFalse(analyzer.isValid("<a><b>>/b></a>"));
        assertFalse(analyzer.isValid("<<a></a>"));
    }
}