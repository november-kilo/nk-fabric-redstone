package net.fabricmc.nkredstone.block.impl;

import net.fabricmc.nkredstone.block.VariableResistor;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class VariableEmitterBlock extends VariableResistor {
    public VariableEmitterBlock(Settings settings) {
        super(settings);
        setDefaultState(stateManager.getDefaultState().with(POWERED, true));
    }

    @Override
    public boolean isLocked(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }
}
