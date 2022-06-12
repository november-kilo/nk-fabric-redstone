package net.fabricmc.nkredstone.block.impl;

import net.fabricmc.nkredstone.block.VariableResistor;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class VariableResistorBlock extends VariableResistor {
    public VariableResistorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (!state.get(POWERED)) {
            return 0;
        }

        return state.get(FACING) == direction ? getOutputLevelWithoutResistance(world, pos, state) - state.get(RESISTANCE) : 0;
    }
}
