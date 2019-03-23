package edu.stts.latihanuts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText edName;
    private RadioGroup rgType;
    private RadioButton rbTea,rbCoffee,rbSmoothies;
    private CheckBox cbPearl, cbPudding, cbRedBean, cbCoconut;
    private Button btnMinus, btnPlus, btnAdd, btnEdit, btnDelete, btnReset;
    private TextView txtQty, txtTotal, txtName;
    private RecyclerView rvOrder;
    private OrderAdapter adapter;
    private ArrayList<Order> arrOrder = new ArrayList<>();
    private long total = 0;
    private int index = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edName = (EditText) findViewById(R.id.editText_name);
        rgType = findViewById(R.id.radioGroup_type);
        rbTea = findViewById(R.id.radioButton_tea);
        rbCoffee = findViewById(R.id.radioButton_coffee);
        rbSmoothies = findViewById(R.id.radioButton_smoothies);
        cbPearl = findViewById(R.id.checkBox_pearl);
        cbPudding = findViewById(R.id.checkBox_pudding);
        cbRedBean = findViewById(R.id.checkBox_red_bean);
        cbCoconut = findViewById(R.id.checkBox_coconut);
        btnMinus = findViewById(R.id.button_minus);
        btnPlus = findViewById(R.id.button_plus);
        txtQty = findViewById(R.id.textView_qty);
        btnAdd = findViewById(R.id.button_add);
        btnEdit = findViewById(R.id.button_edit);
        btnDelete = findViewById(R.id.button_delete);
        btnReset = findViewById(R.id.button_reset);
        rvOrder = findViewById(R.id.recyclerview_order);
        txtName = findViewById(R.id.textView_nama);
        txtTotal = findViewById(R.id.textView_total);
        adapter = new OrderAdapter(arrOrder, new RVClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int posisi) {
                index = posisi;
                Order temp = arrOrder.get(posisi);
                if(temp.getType().equals("Tea")){ rbTea.setChecked(true); }
                else if(temp.getType().equals("Coffee")){ rbCoffee.setChecked(true);}
                else {rbSmoothies.setChecked(true);}
                cbPearl.setChecked(false);cbCoconut.setChecked(false);
                cbPudding.setChecked(false);cbRedBean.setChecked(false);
                for(int i = 0 ; i <temp.getToppings().size();i++){
                    if(temp.getToppings().get(i).equals("Pearl")){ cbPearl.setChecked(true);}
                    if(temp.getToppings().get(i).equals("Red Bean")){ cbRedBean.setChecked(true);}
                    if(temp.getToppings().get(i).equals("Pudding")){ cbPudding.setChecked(true);}
                    if(temp.getToppings().get(i).equals("Coconut")){ cbCoconut.setChecked(true);}
                }
                txtQty.setText(temp.getQty() + "");
            }
        });
        rvOrder.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(MainActivity.this);
        rvOrder.setLayoutManager(lm);
        rbTea.setChecked(true);
        total = 0;


        btnMinus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Integer temp = Integer.parseInt(txtQty.getText().toString());
                if (temp > 1) temp-=1;
                txtQty.setText(temp+"");
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Integer temp = Integer.parseInt(txtQty.getText().toString());
                temp+=1;
                txtQty.setText(temp+"");
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!edName.getText().toString().equals("")){
                    String tempType = ((RadioButton)findViewById(rgType.getCheckedRadioButtonId())).getText().toString();
                    long subtotal = 0;
                    if(tempType.equals("Tea")) subtotal += 23000;
                    else if(tempType.equals("Coffee")) subtotal += 25000;
                    else subtotal += 30000;
                    ArrayList<String> tempTop = new ArrayList<>();
                    if(cbPearl.isChecked()) {tempTop.add("Pearl"); subtotal+= 3000;}
                    if(cbRedBean.isChecked()) {tempTop.add("Red Bean"); subtotal += 3000;}
                    if(cbPudding.isChecked()) {tempTop.add("Pudding"); subtotal += 4000;}
                    if(cbCoconut.isChecked()) {tempTop.add("Coconut"); subtotal += 4000;}
                    Integer tempQty = Integer.parseInt(txtQty.getText().toString());
                    Order temp = new Order(tempType,tempTop,tempQty,tempQty * subtotal);
                    arrOrder.add(temp);
                    total += tempQty * subtotal;
                    txtTotal.setText("Total: Rp " + total);
                    adapter.notifyDataSetChanged();
                    txtName.setText("Hi, "+edName.getText().toString()+"!");
                }else{
                    Toast.makeText(MainActivity.this,"Isi nama terlebih dahulu",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(index != -1) {
                    Order temp = arrOrder.get(index);
                    total -= temp.getSubtotal();
                    txtTotal.setText("Total: Rp " + total);
                    arrOrder.remove(index);
                    index = -1;
                    adapter.notifyDataSetChanged();
                }
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(index != -1) {
                    Order temp = arrOrder.get(index);
                    total -= temp.getSubtotal();

                    String tempType = ((RadioButton)findViewById(rgType.getCheckedRadioButtonId())).getText().toString();
                    long subtotal = 0;
                    if(tempType.equals("Tea")) subtotal += 23000;
                    else if(tempType.equals("Coffee")) subtotal += 25000;
                    else subtotal += 30000;
                    ArrayList<String> tempTop = new ArrayList<>();
                    if(cbPearl.isChecked()) {tempTop.add("Pearl"); subtotal+= 3000;}
                    if(cbRedBean.isChecked()) {tempTop.add("Red Bean"); subtotal += 3000;}
                    if(cbPudding.isChecked()) {tempTop.add("Pudding"); subtotal += 4000;}
                    if(cbCoconut.isChecked()) {tempTop.add("Coconut"); subtotal += 4000;}
                    Integer tempQty = Integer.parseInt(txtQty.getText().toString());

                    temp.setType(tempType);
                    temp.setQty(tempQty);
                    temp.setSubtotal(tempQty * subtotal);
                    temp.setToppings(tempTop);
                    total += tempQty * subtotal;
                    txtTotal.setText("Total: Rp " + total);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                edName.setText("");
                rbTea.setChecked(true);
                cbPudding.setChecked(false);
                cbPearl.setChecked(false);
                cbRedBean.setChecked(false);
                cbCoconut.setChecked(false);
                arrOrder.clear();
                adapter.notifyDataSetChanged();
                total = 0;
                txtTotal.setText("Total: Rp 0");
                txtName.setText(("Hi, !"));
                txtQty.setText("1");
            }
        });
    }
}
