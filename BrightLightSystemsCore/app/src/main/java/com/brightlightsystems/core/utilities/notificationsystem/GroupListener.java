package com.brightlightsystems.core.utilities.notificationsystem;

/**
 * This interface describes behaviour of the group listener.
 * All calls to described bellow methods are done through
 * Dispatcher when certain notifications was posted through Publisher
 * Created by Michael on 11/11/2015.
 */
public interface GroupListener
{
    public void onAddGroup(GroupMessage message);
    public void onRemoveGroup(GroupMessage message);
    public void onUpdateGroup(GroupMessage message);
    public void onUpdateMultiGroups(GroupMessage message);
    public void onActivatedGroup(GroupMessage message);
    public void onDeactivateGroup(GroupMessage message);
    public void onSyncGroups(GroupMessage message);
    public void onRemoveSubgroups(GroupMessage message);
}
