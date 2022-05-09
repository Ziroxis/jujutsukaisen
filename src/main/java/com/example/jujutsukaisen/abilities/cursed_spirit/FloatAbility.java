package com.example.jujutsukaisen.abilities.cursed_spirit;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousAbility;

public class FloatAbility extends ContinuousAbility {

    public static final FloatAbility INSTANCE = new FloatAbility();

    public FloatAbility()
    {
        super("Float", AbilityCategories.AbilityCategory.TECHNIQUE);
    }
}
