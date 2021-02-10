package gg.vh.modsuite.base.item.impl;

import gg.vh.modsuite.base.item.base.SuiteItem;
import gg.vh.modsuite.base.user.User;
import gg.vh.modsuite.file.DataFile;

public class SampleItem extends SuiteItem {

    public SampleItem(DataFile dataFile) {
        super(dataFile);
    }

    @Override
    public void onInteract(User user) {
        user.message("This works.");
    }
}
