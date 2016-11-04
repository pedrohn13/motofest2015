package br.edu.ufcg.embedded.motofest.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.ufcg.embedded.motofest.R;

public class MenuAdapter extends BaseAdapter {
    private final List<ItemMenu> items;
    private final Context context;

    public MenuAdapter(Context context, List<ItemMenu> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int item) {
        return items.get(item);
    }

    @Override
    public long getItemId(int item) {
        return item;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.item_list_menu, null);
        }
        TextView label = (TextView) view.findViewById(R.id.itemLabel);
        label.setText(items.get(position).getNome());
        ImageView icon = (ImageView) view.findViewById(R.id.itemIcon);
        icon.setImageResource(items.get(position).getImage());
        return view;
    }
}
