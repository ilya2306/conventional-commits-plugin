package com.s4.conventionalcommits.ui.dialog;

import org.jetbrains.annotations.Nullable;
import com.s4.conventionalcommits.CommitType;
import com.s4.conventionalcommits.localization.PluginBundle;

import javax.swing.*;
import java.util.Objects;

public class CommitPanel {
    private JPanel contentPane;
    private JComboBox<CommitType> commitType;
    private JComboBox<String> commitScope;
    private JTextField commitSubject;
    private JTextArea commitBody;
    private JCheckBox commitBodyWrap;
    private JTextArea commitBreakingChanges;
    private JCheckBox commitSkipCi;
    private JTextField commitClosedTasks;
    private JLabel commitTypeLabel;
    private JLabel commitScopeLabel;
    private JLabel commitSubjectLabel;
    private JLabel commitBodyLabel;
    private JLabel commitBreakingChangesLabel;
    private JLabel commitClosedTasksLabel;

    public CommitPanel() {
        setUI();
    }

    private void setUI() {
        setLabels();
        setBorders();
        addCommitTypes();

        commitBody.setLineWrap(true);
        commitBody.setWrapStyleWord(true);
    }

    private void setLabels() {
        commitTypeLabel.setText(PluginBundle.get("commitPanel.commitType.label"));
        commitTypeLabel.setToolTipText(PluginBundle.get("commitPanel.commitType.tooltip"));

        commitScopeLabel.setText(PluginBundle.get("commitPanel.commitScope.label"));
        commitScopeLabel.setToolTipText(PluginBundle.get("commitPanel.commitScope.tooltip"));

        commitSubjectLabel.setText(PluginBundle.get("commitPanel.commitSubject.label"));
        commitSubjectLabel.setToolTipText(PluginBundle.get("commitPanel.commitSubject.tooltip"));

        commitBodyLabel.setText(PluginBundle.get("commitPanel.commitBody.label"));
        commitBodyLabel.setToolTipText(PluginBundle.get("commitPanel.commitBody.tooltip"));

        commitBreakingChangesLabel.setText(PluginBundle.get("commitPanel.commitBreakingChanges.label"));
        commitBreakingChangesLabel.setToolTipText(PluginBundle.get("commitPanel.commitBody.tooltip"));

        commitClosedTasksLabel.setText(PluginBundle.get("commitPanel.commitClosedTasks.label"));
        commitClosedTasksLabel.setToolTipText(PluginBundle.get("commitPanel.commitClosedTasks.tooltip"));
    }

    private void setBorders() {
        commitBody.setBorder(BorderFactory.createEmptyBorder());
        commitBreakingChangesLabel.setBorder(BorderFactory.createEmptyBorder());
    }

    private void addCommitTypes() {
        commitType.addItem(new CommitType("build", PluginBundle.get("commitType.build.description")));
        commitType.addItem(new CommitType("ci", PluginBundle.get("commitType.ci.description")));
        commitType.addItem(new CommitType("docs", PluginBundle.get("commitType.docs.description")));
        commitType.addItem(new CommitType("feat", PluginBundle.get("commitType.feat.description")));
        commitType.addItem(new CommitType("fix", PluginBundle.get("commitType.fix.description")));
        commitType.addItem(new CommitType("perf", PluginBundle.get("commitType.perf.description")));
        commitType.addItem(new CommitType("refactor", PluginBundle.get("commitType.refactor.description")));
        commitType.addItem(new CommitType("style", PluginBundle.get("commitType.style.description")));
        commitType.addItem(new CommitType("test", PluginBundle.get("commitType.test.description")));
    }


    public String getCommitType() {
        Object selectedCommitType = commitType.getSelectedItem();

        if (selectedCommitType instanceof CommitType) {
            return ((CommitType) selectedCommitType).type();
        } else if (selectedCommitType != null) {
            return selectedCommitType.toString();
        }

        return "";
    }

    public void setCommitType(@Nullable String nextCommitType) {
        if (nextCommitType != null) {

            ComboBoxModel<CommitType> model = commitType.getModel();

            int foundIndex = -1;
            for (int i = 0; i < model.getSize(); i++) {
                if (Objects.equals(((CommitType) model.getElementAt(i)).getType(), nextCommitType.toLowerCase())) {
                    foundIndex = i;
                }
            }

            if (foundIndex == -1 && commitType.isEditable()) {
                commitType.setSelectedItem(nextCommitType.toLowerCase());
            } else {
                commitType.setSelectedIndex(foundIndex);
            }
        }
    }

    public String getCommitScope() {
        Object selectedCommitScope = commitScope.getSelectedItem();

        if (selectedCommitScope != null) {
            return selectedCommitScope.toString();
        }

        return null;
    }

    public void setCommitScope(String nextCommitScope) {
        if (nextCommitScope != null) {

            ComboBoxModel<String> model = commitScope.getModel();

            if (commitScope.getItemCount() > 0) {
                int foundIndex = -1;
                for (int i = 0; i < model.getSize(); i++) {
                    if (Objects.equals((model.getElementAt(i)).toLowerCase(), nextCommitScope.toLowerCase())) {
                        foundIndex = i;
                    }
                }

                if (foundIndex == -1 && commitType.isEditable()) {
                    commitType.setSelectedItem(nextCommitScope);
                } else {
                    commitType.setSelectedIndex(foundIndex);
                }
            } else {
                commitScope.setSelectedItem(nextCommitScope);
            }
        }
    }

    public String getCommitSubject() {
        return commitSubject.getText();
    }

    public void setCommitSubject(String nextCommitSubject) {
        commitSubject.setText(nextCommitSubject);
    }

    public String getCommitBody() {
        return commitBody.getText();
    }

    public void setCommitBody(String nextCommitBody) {
        commitBody.setText(nextCommitBody);
    }

    public Boolean getCommitBodyWrap() {
        return commitBodyWrap.isSelected();
    }

    public void setCommitBodyWrap(Boolean nextCommitBodyWrap) {
        commitBodyWrap.setSelected(nextCommitBodyWrap);
    }

    public String getCommitBreakingChanges() {
        return commitBreakingChanges.getText();
    }

    public void setCommitBreakingChanges(String nextCommitBreakingChanges) {
        commitBreakingChanges.setText(nextCommitBreakingChanges);
    }

    public Boolean getCommitSkipCi() {
        return commitSkipCi.isSelected();
    }

    public void setCommitSkipCi(Boolean nextCommitSkipCi) {
        commitSkipCi.setSelected(nextCommitSkipCi);
    }

    public String getCommitClosedTasks() {
        return commitClosedTasks.getText();
    }

    public void setCommitClosedTasks(String nextCommitClosedTasks) {
        commitClosedTasks.setText(nextCommitClosedTasks);
    }

    public JPanel getMainPanel() {
        return contentPane;
    }

}
