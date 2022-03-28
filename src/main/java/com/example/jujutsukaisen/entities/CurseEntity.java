package com.example.jujutsukaisen.entities;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.client.renderer.IDynamicRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraftforge.common.IExtensibleEnum;

import javax.annotation.Nullable;


public abstract class CurseEntity extends CreatureEntity implements IDynamicRenderer {
    protected String[] textures;
    protected boolean needsEntityDataUpdate;
    protected float statXPDrop = 1;

    private static final DataParameter<String> TEXTURE = EntityDataManager.defineId(CurseEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> ANIMATION = EntityDataManager.defineId(CurseEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> CURSE_GRADE = EntityDataManager.defineId(CurseEntity.class, DataSerializers.INT);

    private int yul;
    protected int threat = 2, maxML = 100, minML = 0;
    private Goal currentGoal, previousGoal;
    //protected List<AiSpellEntry> SPELL_POOL = new ArrayList<AiSpellEntry>();

    public CurseEntity(EntityType type, World world)
    {
        this(type, world, null);
    }

    public CurseEntity(EntityType type, World world, String[] textures)
    {
        super(type, world);
        this.xpReward = this.threat;
        this.textures = textures;
        this.chooseTexture();
    }

    public static AttributeModifierMap.MutableAttribute createAttributes()
    {
        return MobEntity.createMobAttributes().add(Attributes.ATTACK_DAMAGE);
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.getEntityData().define(TEXTURE, "");
        this.getEntityData().define(CURSE_GRADE, 1);
        this.getEntityData().define(ANIMATION, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt)
    {
        super.addAdditionalSaveData(nbt);

        nbt.putInt("curse_grade", this.getCurseGrade());
        nbt.putInt("yul", this.yul);
        nbt.putInt("threat", this.threat);
        nbt.putInt("animation", this.getAnimation());

        nbt.putString("texture", this.getMobTexture());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt)
    {
        super.readAdditionalSaveData(nbt);

        this.setCurseGrade(nbt.getInt("curse_grade"));
        this.yul = nbt.getInt("yul");
        this.threat = nbt.getInt("threat");
        this.setAnimation(nbt.getInt("animation"));

        this.setTexture(nbt.getString("texture"));
    }

    @Override
    public String getDefaultTexture()
    {
        if (this.textures != null)
            return this.textures[0];
        return "null";
    }

    @Override
    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag)
    {
        spawnData = super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);

        this.setLeftHanded(false);

        if (this.textures != null && this.textures.length > 0)
        {
            int id = this.random.nextInt(this.textures.length);
            this.setTexture(this.textures[id]);
        } else if (Beapi.isNullOrEmpty(this.getMobTexture()))
            this.setTexture(this.getDefaultTexture());


        return spawnData;
    }



    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn)
    {
    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
/*
        if (cause.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) cause.getEntity();
            IPlayerHandler playercap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));

            Beapi.experienceMultiplier(player, this.magicXPDrop);

            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketMagicExpSync(playercap.returnMagicExp(), player.getId()));

        }
*/
    }

    @Override
    protected void tickDeath() {
        super.tickDeath();
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getWalkTargetValue(BlockPos pos, IWorldReader world)
    {
        Block block = world.getBlockState(pos.below()).getBlock();
        boolean flag1 = !world.getBlockState(pos).isAir();
        boolean flag2 = pos.getY() < 150;
        boolean flag3 = block.getBlock() == Blocks.GRASS_BLOCK || block.getBlock() == Blocks.SAND || block.getBlock() == Blocks.STONE;
        float weight = (64 / pos.getY()) * 10;
        return flag1 && flag2 && flag3 ? weight : 0;
    }

    @Override
    public boolean checkSpawnRules(IWorld world, SpawnReason reason)
    {
        return this.getWalkTargetValue(new BlockPos(this.position()), world) > 0.0F;
    }

    @Override
    public void checkDespawn()
    {
        if (this.hasCustomName())
            return;

        super.checkDespawn();
    }

    @Override
    public String getMobTexture()
    {
        return this.getEntityData().get(TEXTURE);
    }

    protected void setTexture(String id)
    {
        this.getEntityData().set(TEXTURE, id);
    }

    protected void chooseTexture()
    {
        if(!this.level.isClientSide){
            if (this.textures != null && this.textures.length > 0)
            {
                int id = this.random.nextInt(this.textures.length);
                this.setTexture(this.textures[id]);
            }
        }
    }



    public int getCurseGrade()
    {
        return this.entityData.get(CURSE_GRADE);
    }

    public void setCurseGrade(int value)
    {
        this.entityData.set(CURSE_GRADE, value);
    }

    public int getYul()
    {
        return this.yul;
    }

    public void setYul(double value)
    {
        this.yul = (int) Math.floor(value);
    }

    public int getAnimation()
    {
        return this.entityData.get(ANIMATION);
    }

    public void setAnimation(int value)
    {
        this.entityData.set(ANIMATION, value);
    }

    public Goal getCurrentGoal()
    {
        return this.currentGoal;
    }

    public Goal getPreviousGoal()
    {
        return this.previousGoal;
    }

    public void setCurrentGoal(Goal goal)
    {
        this.currentGoal = goal;
    }

    public void setPreviousGoal(Goal goal)
    {
        this.previousGoal = goal;
    }

    public void addThreat(int threat)
    {
        this.threat += threat;
    }

    public int getThreat()
    {
        return this.threat;
    }

    public void setThreat(int threat)
    {
        this.threat = threat;
    }

    public void queueEntityDataUpdate()
    {
        this.needsEntityDataUpdate = true;
    }

    @Override
    public void tick()
    {
        super.tick();

        if (!this.level.isClientSide)
        {
            if (this.needsEntityDataUpdate)
            {
                /*IEntityStats props = EntityStatsCapability.get(this);
                WyNetwork.sendToAllTrackingAndSelf(new SSyncEntityStatsPacket(this.getId(), props), this);*/

                this.needsEntityDataUpdate = false;
            }

            int targetID = this.getTarget() != null ? this.getTarget().getId() : 0;
            //NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this), new PacketSyncBCEntityTarget(this.getId(), targetID));

        }
    }

    public static enum Animation implements IExtensibleEnum
    {
        NONE(0), FLINTLOCK_POINTING(1), CLEAVE_ATTACK(2), SHOCKWAVE(3), BLOCK(4);

        private int id;

        private Animation(int id)
        {
            this.id = id;
        }

        public int getId()
        {
            return this.id;
        }

        public static Animation create(String name, int id)
        {
            throw new IllegalStateException("Enum not extended");
        }
    }



    /*
    public void applySpellCD(AbstractSpell spell){
        String nbtName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_cd";
        this.getPersistentData().putInt(nbtName, spell.getCooldown());
    }
    */





    public float getStatXPDrop() {
        return statXPDrop;
    }
}
