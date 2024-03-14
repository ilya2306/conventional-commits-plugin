package com.s4.conventionalcommits;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.CommitMessageI;
import com.intellij.openapi.vcs.VcsDataKeys;
import com.intellij.openapi.vcs.ui.Refreshable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.s4.conventionalcommits.ui.dialog.CommitDialog;

public class CommitMessageAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent actionEvent) {
        final CommitMessageI commitPanel = getCommitPanel(actionEvent);
        if (commitPanel == null) {
            return;
        }
        Project project = actionEvent.getProject();
        assert project != null;

        CommitMessage commitMessage = parseExistingCommitMessage(commitPanel);
        CommitDialog commitDialog = new CommitDialog(project, commitMessage);

        commitDialog.show();
        commitDialog.pack();
        commitDialog.centerRelativeToParent();

        if (commitDialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
            commitPanel.setCommitMessage(commitDialog.getCommitMessage());
        }
    }

    private CommitMessage parseExistingCommitMessage(CommitMessageI commitPanel) {
        if (commitPanel instanceof CheckinProjectPanel) {
            String commitMessage = ((CheckinProjectPanel) commitPanel).getCommitMessage();

            return CommitMessage.parse(commitMessage);
        }

        return null;
    }

    @Nullable
    private static CommitMessageI getCommitPanel(@Nullable AnActionEvent e) {
        if (e == null) {
            return null;
        }
        Refreshable data = Refreshable.PANEL_KEY.getData(e.getDataContext());
        if (data instanceof CommitMessageI) {
            return (CommitMessageI) data;
        }
        return VcsDataKeys.COMMIT_MESSAGE_CONTROL.getData(e.getDataContext());
    }
}
