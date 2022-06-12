package net.fabricmc.nkredstone.block.impl;

import net.fabricmc.nkredstone.block.AbstractLogicGate;

public class XorGateBlock extends AbstractLogicGate {
    public XorGateBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean condition(int leftInput, int rightInput) {
        return Boolean.logicalXor(leftInput > 0, rightInput > 0);
    }
}
