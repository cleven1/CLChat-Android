package com.cleven.clchat.fragment.contack.views;

import com.cleven.clchat.model.CLUserBean;

import java.util.Comparator;

/**
 * 专用于按首字母排序
 *
 * @author nanchen
 * @fileName WaveSideBarView
 * @packageName com.nanchen.wavesidebarview
 * @date 2016/12/27  16:19
 * @github https://github.com/nanchen2251
 */

public class LetterComparator implements Comparator<CLUserBean>{

    @Override
    public int compare(CLUserBean contactModel, CLUserBean t1) {
        if (contactModel == null || t1 == null){
            return 0;
        }
        String lhsSortLetters = contactModel.getIndex().substring(0, 1).toUpperCase();
        String rhsSortLetters = t1.getIndex().substring(0, 1).toUpperCase();
        return lhsSortLetters.compareTo(rhsSortLetters);
    }
}
