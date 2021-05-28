package com.example.phamhuunamkt2bai2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListviewAdapter extends BaseAdapter {
    ArrayList<Lich> arrayList;

    public ListviewAdapter(ArrayList<Lich> arrayList) {
        this.arrayList = arrayList;
    }
    public class ListViewItem{
        TextView name,ngaythi,giothi,tongsomon;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ListViewItem listViewItem = null;
        if(view == null){
            view = View.inflate(parent.getContext(),R.layout.listviewrow,null);
            listViewItem = new ListViewItem();
            listViewItem.name = view.findViewById(R.id.Nametxt);
            listViewItem.ngaythi = view.findViewById(R.id.giothi);
            listViewItem.giothi = view.findViewById(R.id.ngaythi);
            listViewItem.tongsomon = view.findViewById(R.id.amount);
            view.setTag(listViewItem);
        }
        else {
        listViewItem =(ListViewItem) view.getTag();
        }
        Lich lich = arrayList.get(position);
        listViewItem.name.setText(lich.getName());
        listViewItem.ngaythi.setText(lich.getDates());
        listViewItem.giothi.setText(lich.getTimes());
        listViewItem.tongsomon.setText(lich.getIschecked());
        return view;
    }
}
