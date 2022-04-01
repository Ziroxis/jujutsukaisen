package com.example.jujutsukaisen.particles;

import net.minecraft.world.World;

import java.io.Serializable;

public abstract class ParticleEffect implements Serializable {
    public abstract void spawn(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ);

}
