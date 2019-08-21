package dds.frba.utn.quemepongo.Helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import dds.frba.utn.quemepongo.R;


public abstract class ListSwipeHelper<T extends RecyclerView.Adapter> extends ItemTouchHelper.SimpleCallback {
    public static final int LEFT = ItemTouchHelper.LEFT;
    public static final int RIGHT = ItemTouchHelper.RIGHT;

    private Drawable icon;
    private  ColorDrawable background;
    private Context context;

    public ListSwipeHelper(Context context) {
        super(0,ItemTouchHelper.LEFT);
        icon = ContextCompat.getDrawable(context, R.drawable.ic_delete_gray_24dp);
        background = new ColorDrawable(Color.RED);
        this.context = context;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        onSwipe(
                viewHolder.getAdapterPosition(),
                i == LEFT ? LEFT : RIGHT
        );
    }


    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 30;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();
        if (dX < 0) { // Swiping izquierdo
            background = getColorDirection(LEFT);
            icon = ContextCompat.getDrawable(context,getDirectionIcon(LEFT));

            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else if (dX > 0) { // Swiping to the right
            background = getColorDirection(RIGHT);
            icon = ContextCompat.getDrawable(context,getDirectionIcon(RIGHT));

            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                    itemView.getBottom());

        }else { // sin swipe
            background.setBounds(0, 0, 0, 0);
            icon.setBounds(0,0,0,0);
        }

        background.draw(c);
        icon.draw(c);
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, enableDirections());
    }

    public abstract void onSwipe(int index, int direction);
    public int enableDirections(){
        return LEFT | RIGHT;
    }
    public abstract ColorDrawable getColorDirection(int direction);
    public abstract int getDirectionIcon(int dir);
}
