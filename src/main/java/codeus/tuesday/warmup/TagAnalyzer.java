package codeus.tuesday.warmup;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagAnalyzer {

    // Patterns if you want to use them
    private static final Pattern TAG_PATTERN = Pattern.compile("<([^<>]+)>");
    private static final Pattern VALID_TAG_NAME = Pattern.compile("^/?[a-zA-Z]+$");

    /**
     * Analyzes if tags in text are properly nested
     * Rules:
     * 1. Tags format: <tag> and </tag>
     * 2. Tags can contain letters only
     * 3. Tags can be nested
     * 4. Each opening tag must have matching closing tag
     * 5. Tags must close in correct order
     * <p>
     * Examples:
     * "<a><b></b></a>" → true
     * "<a><b></a></b>" → false
     * "<a></a><b></b>" → true
     * "<a>" → false
     * "text" → true (no tags is valid)
     * "<a><b>" → false
     * "<1a></1a>" → false (numbers not allowed)
     *
     * Implementation steps:
     * 1. Initialize stack to store opening tags
     * 2. Create matcher to find all tag patterns in text
     * 3. Track position of last valid tag match
     * 4. For each found tag:
     *    a. Check no '<' exists between valid tags
     *    b. Extract tag content between < >
     *    c. Validate tag name contains only letters
     *    d. If closing tag (starts with /):
     *       - Check stack has matching opening tag
     *       - Pop and compare with current tag name
     *    e. If opening tag:
     *       - Push to stack
     * 5. Check no stray '<' after last valid tag
     * 6. Verify all tags were closed (stack empty)
     *
     */
    public boolean isValid(String text) {
        Stack<String> tags = new Stack<>();
        Matcher matcher = TAG_PATTERN.matcher(text);

        // Keep track of the last match end position
        int lastEnd = 0;

        while (matcher.find()) {
            // Check if there's a gap between matches that contains '<'
            if (text.substring(lastEnd, matcher.start()).contains("<")) {
                return false;
            }
            lastEnd = matcher.end();

            String tagContent = matcher.group(1);

            // Validate tag content format
            if (!VALID_TAG_NAME.matcher(tagContent).matches()) {
                return false;
            }

            boolean isClosing = tagContent.startsWith("/");
            String tagName = isClosing ? tagContent.substring(1) : tagContent;

            if (isClosing) {
                if (tags.isEmpty() || !tags.pop().equals(tagName)) {
                    return false;
                }
            } else {
                tags.push(tagName);
            }
        }

        // Check if there's any remaining '<' after the last match
        if (text.substring(lastEnd).contains("<")) {
            return false;
        }

        return tags.isEmpty();
    }
}

