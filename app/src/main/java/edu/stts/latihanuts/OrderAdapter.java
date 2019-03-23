package edu.stts.latihanuts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<Order> orderlist;
    private static RVClickListener mylistener;
    public OrderAdapter(ArrayList<Order> orderlist, RVClickListener mylistener){
        this.orderlist = orderlist;
        this.mylistener = mylistener;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_item_order, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        Order temp = orderlist.get(position);
        holder.tvQtyType.setText(temp.getQty() + " " + temp.getType());
        String tempStr = "";
        for(int i = 0; i <temp.getToppings().size();i++){
            tempStr += temp.getToppings().get(i) + " ";
        }
        holder.tvToppings.setText(tempStr);
        holder.tvSubtotal.setText(""+temp.getSubtotal());

    }

    @Override
    public int getItemCount() {
        return (orderlist!=null) ? orderlist.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvQtyType, tvToppings, tvSubtotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQtyType = itemView.findViewById(R.id.textView_qty_type);
            tvToppings = itemView.findViewById(R.id.textView_toppings);
            tvSubtotal = itemView.findViewById(R.id.textView_subtotal);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mylistener.recyclerViewListClicked(v, ViewHolder.this.getLayoutPosition());
                }
            });
        }
    }
}
