package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.ItemType;
import com.conquestreforged.core.block.annotation.Name;
import com.conquestreforged.core.block.annotation.State;
import com.conquestreforged.core.block.props.Props;
import net.minecraft.block.BlockBed;
import net.minecraft.item.ItemBed;

/**
 * @Author <dags@dags.me>
 */
@Name("%s_bed")
@State("/template/blockstate/bed.json")
@ItemType(ItemBed.class)
public class Bed extends BlockBed {
    public Bed(Props props) {
        super(props.dye(), props.toProperties());
    }
}
