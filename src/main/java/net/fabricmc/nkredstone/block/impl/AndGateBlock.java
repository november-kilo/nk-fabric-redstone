package net.fabricmc.nkredstone.block.impl;

import net.fabricmc.nkredstone.block.AbstractLogicGate;

public class AndGateBlock extends AbstractLogicGate {
    public AndGateBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean condition(int leftInput, int rightInput) {
        return Boolean.logicalAnd(leftInput > 0, rightInput > 0);
    }
}
