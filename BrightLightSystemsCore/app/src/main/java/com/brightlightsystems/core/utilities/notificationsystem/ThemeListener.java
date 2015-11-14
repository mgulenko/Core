package com.brightlightsystems.core.utilities.notificationsystem;

/**
 * Created by Michael on 11/11/2015.
 */
public interface ThemeListener
{
    public void onAddTheme(ThemeMessage message);
    public void onRemoveTheme(ThemeMessage message);
    public void onUpdateTheme(ThemeMessage message);
    public void onUpdateMultiThemes(ThemeMessage message);
    public void onActivatedTheme(ThemeMessage message);
    public void onDeactivateTheme(ThemeMessage message);
    public void onRemoveSubthemes(ThemeMessage message);
    public void onSyncThemes(ThemeMessage message);
}
