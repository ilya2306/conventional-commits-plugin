package com.s4.conventionalcommits.ui.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.s4.conventionalcommits.CommitMessage;
import com.s4.conventionalcommits.localization.PluginBundle;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CommitDialog extends DialogWrapper {
    private final CommitPanel commitPanel;

    public CommitDialog(@Nullable Project project, CommitMessage commitMessage) {
        super(project);

        commitPanel = new CommitPanel();
        setTitle(PluginBundle.get("commitPanel.title"));
        setOKButtonText(PluginBundle.get("commitPanel.action.ok"));
        setCancelButtonText(PluginBundle.get("commitPanel.action.cancel"));

        if (commitMessage != null) {
            commitPanel.setCommitType(commitMessage.getType());
            commitPanel.setCommitScope(commitMessage.getScope());
            commitPanel.setCommitSubject(commitMessage.getSubject());
            commitPanel.setCommitBody(commitMessage.getBody());
            commitPanel.setCommitSubject(commitMessage.getSubject());
            commitPanel.setCommitBreakingChanges(commitMessage.getBreakingChanges());
            commitPanel.setCommitBodyWrap(commitMessage.getWrapBody());
            commitPanel.setCommitClosedTasks(commitMessage.getClosedTasks());
            commitPanel.setCommitSkipCi(commitMessage.getSkipCi());
        }

        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return commitPanel.getMainPanel();
    }

    public String getCommitMessage() {
        return new CommitMessage(
                commitPanel.getCommitType(),
                commitPanel.getCommitScope(),
                commitPanel.getCommitSubject(),
                commitPanel.getCommitBody(),
                commitPanel.getCommitBodyWrap(),
                commitPanel.getCommitBreakingChanges(),
                commitPanel.getCommitClosedTasks(),
                commitPanel.getCommitSkipCi()
        ).toString();
    }
}
