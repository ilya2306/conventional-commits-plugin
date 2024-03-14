package icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public interface PluginIcons {

    Icon ICON = IconLoader.getIcon("/icons/icon.png",PluginIcons.class);

    Icon EDIT = IconLoader.getIcon("/icons/edit.svg",PluginIcons.class);
}
