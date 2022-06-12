package net.fabricmc.nkredstone.block.impl;

import net.fabricmc.nkredstone.block.VariableResistor;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class VariableInverterBlock extends VariableResistor {
    public VariableInverterBlock(Settings settings) {
        super(settings);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (state.get(POWERED)) {
            return 0;
        }

        return state.get(FACING) == direction ? getOutputLevel(world, pos, state) : 0;
    }
}
