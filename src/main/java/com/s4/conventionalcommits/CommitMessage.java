package com.s4.conventionalcommits;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jgoodies.common.base.Strings.isNotBlank;

public class CommitMessage {
    private static final int MAX_LINE_LENGTH = 72;
    public static final Pattern COMMIT_FIRST_LINE_FORMAT = Pattern.compile("^([a-z]+)(\\((.+)\\))?: (.+)");
    public static final Pattern COMMIT_CLOSES_FORMAT = Pattern.compile("Closes (.+)");

    private String type;
    private String scope;
    private String subject;
    private String body;
    private Boolean wrapBody;
    private String breakingChanges;
    private String closedTasks;
    private Boolean skipCi;

    private CommitMessage() {
    }

    public CommitMessage(String commitType, String commitScope, String commitSubject, String commitBody, Boolean commitWrapBody, String commitBreakingChanges, String commitClosedTasks, Boolean commitSkipCi) {
        this.type = commitType;
        this.scope = commitScope;
        this.subject = commitSubject;
        this.body = commitBody;
        this.wrapBody = commitWrapBody;
        this.breakingChanges = commitBreakingChanges;
        this.closedTasks = commitClosedTasks;
        this.skipCi = commitSkipCi;
    }

    public static CommitMessage parse(String existingMessage) {
        CommitMessage commitMessage = new CommitMessage();

        try {
            Matcher matcher = COMMIT_FIRST_LINE_FORMAT.matcher(existingMessage);
            if (!matcher.find()) return commitMessage;

            commitMessage.type = matcher.group(1).toUpperCase();
            commitMessage.scope = matcher.group(3);
            commitMessage.subject = matcher.group(4);

            String[] strings = existingMessage.split("\n");
            if (strings.length < 2) return commitMessage;

            int pos = 1;
            StringBuilder stringBuilder;

            stringBuilder = new StringBuilder();
            for (; pos < strings.length; pos++) {
                String lineString = strings[pos];
                if (lineString.startsWith("BREAKING") || lineString.startsWith("Closes") || lineString.equalsIgnoreCase("[skip ci]"))
                    break;
                stringBuilder.append(lineString).append('\n');
            }
            commitMessage.body = stringBuilder.toString().trim();

            stringBuilder = new StringBuilder();
            for (; pos < strings.length; pos++) {
                String lineString = strings[pos];
                if (lineString.startsWith("Closes") || lineString.equalsIgnoreCase("[skip ci]")) break;
                stringBuilder.append(lineString).append('\n');
            }
            commitMessage.breakingChanges = stringBuilder.toString().trim().replace("BREAKING CHANGE: ", "");

            matcher = COMMIT_CLOSES_FORMAT.matcher(existingMessage);
            stringBuilder = new StringBuilder();
            while (matcher.find()) {
                stringBuilder.append(matcher.group(1)).append(',');
            }
            if (stringBuilder.length() > 0) stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            commitMessage.closedTasks = stringBuilder.toString();

            commitMessage.skipCi = existingMessage.contains("[skip ci]");
        } catch (RuntimeException e) {
        }

        return commitMessage;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(type);

        if (isNotBlank(scope)) {
            builder
                    .append('(')
                    .append(scope)
                    .append(')');
        }

        builder
                .append(": ")
                .append(subject);

        final String lineSeparator = "\n";

        if (isNotBlank(body)) {
            builder
                    .append(lineSeparator.repeat(2))
                    .append(getWrappedText(body, wrapBody));
        }

        if (isNotBlank(breakingChanges)) {
            String breakingChangesString = "BREAKING CHANGE: " + breakingChanges;
            builder
                    .append(lineSeparator.repeat(2))
                    .append(getWrappedText(breakingChangesString, wrapBody));
        }

        if (isNotBlank(closedTasks)) {

            builder.append(lineSeparator);

            for (String closedIssue : closedTasks.split(",")) {
                builder
                        .append(lineSeparator)
                        .append("Closes ")
                        .append(formatClosedIssue(closedIssue));
            }
        }

        if (skipCi) {
            builder
                    .append(lineSeparator.repeat(2))
                    .append("[skip ci]");
        }

        return builder.toString();
    }

    private String getWrappedText(String text, Boolean shouldWrap) {
        if (shouldWrap) {
            return WordUtils.wrap(text, CommitMessage.MAX_LINE_LENGTH, "\n", true);
        }

        return text;
    }

    private String formatClosedIssue(String closedIssue) {
        String trimmed = closedIssue.trim();
        return (StringUtils.isNumeric(trimmed) ? "#" : "") + trimmed;
    }

    public String getType() {
        return type;
    }

    public String getScope() {
        return scope;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public Boolean getWrapBody() {
        if (wrapBody == null) return false;
        return wrapBody;
    }

    public String getBreakingChanges() {
        return breakingChanges;
    }

    public String getClosedTasks() {
        return closedTasks;
    }

    public Boolean getSkipCi() {
        if (skipCi == null) return false;
        return skipCi;
    }
}
