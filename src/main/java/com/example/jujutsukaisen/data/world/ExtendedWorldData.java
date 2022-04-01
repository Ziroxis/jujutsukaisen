package com.example.jujutsukaisen.data.world;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtendedWorldData extends WorldSavedData {

    private static final String IDENTIFIER = "jujutsukaisen";

    public static Map<String, ExtendedWorldData> loadedExtWorlds = new HashMap<>();
    private List<int[][]> abilityProtections = new ArrayList<int[][]>();

    public ExtendedWorldData()
    {
        super(IDENTIFIER);
    }


    public ExtendedWorldData(String identifier)
    {
        super(identifier);
    }


    public boolean isInsideRestrictedArea(int posX, int posY, int posZ) {
        if (this.abilityProtections.size() <= 0)
            return false;

        for (int[][] area : this.abilityProtections) {
            int[] minPos = area[0];
            int[] maxPos = area[1];

            if (minPos.length <= 0 || maxPos.length <= 0)
                continue;

            if (posX > minPos[0] && posX < maxPos[0] && posY > minPos[1] && posY < maxPos[1] && posZ > minPos[2] && posZ < maxPos[2]) {
                return true;
            }
        }

        return false;
    }

    public static ExtendedWorldData get(World world) {
        if (world == null)
            return null;

        ExtendedWorldData worldExt = null;
        String worldType = "overworld";
        if (loadedExtWorlds.containsKey(worldType)) {
            worldExt = loadedExtWorlds.get(worldType);
            return worldExt;
        }

        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = world.getServer().getLevel(World.OVERWORLD);
            worldExt = serverWorld.getDataStorage().computeIfAbsent(ExtendedWorldData::new, IDENTIFIER);
        } else
            worldExt = new ExtendedWorldData();

        loadedExtWorlds.put(worldType, worldExt);
        return worldExt;

		/*ExtendedWorldData worldExt = null;
		String worldType = "overworld";//world.getDimension().getType().getRegistryName().getPath();
		// NOTE(Wynd): Because at the moment the id is always overworld this can cause problems, for example this loadedExtWorlds list is not cleared upon joining a new world.
		// This means it is possible to join a different world using the data from another world. To solve this problem (which is only a problem client sided) there's an event subscriber
		// in SyncEvents::onEntityJoinsWorld that clears the list client sided only. A dirty fix but a fix.

		if (loadedExtWorlds.containsKey(worldType))
		{
			worldExt = loadedExtWorlds.get(worldType);
			return worldExt;
		}

		if (world instanceof ServerWorld)
		{
			ServerWorld serverWorld = world.getServer().getWorld(DimensionType.OVERWORLD);
			//world.increaseMaxEntityRadius(50);
			ExtendedWorldData worldSavedData = serverWorld.getSavedData().get(ExtendedWorldData::new, IDENTIFIER);
			if (worldSavedData != null)
			{
				worldExt = worldSavedData;
			}
			else
			{
				worldExt = new ExtendedWorldData();
				serverWorld.getSavedData().set(worldExt);
			}
		}
		else
		{
			worldExt = new ExtendedWorldData();
		}

		loadedExtWorlds.put(worldType, worldExt);
		return worldExt;*/
    }

    @Override
    public void load(CompoundNBT nbt) {
        CompoundNBT protectedAreas = nbt.getCompound("protectedAreas");
        this.abilityProtections.clear();
        for (int i = 0; i <= protectedAreas.getAllKeys().size(); i++) {
            int[] minPos = protectedAreas.getIntArray("minPos_" + i);
            int[] maxPos = protectedAreas.getIntArray("maxPos_" + i);

            if (minPos.length == 3 && maxPos.length == 3) {
                this.abilityProtections.add(new int[][]
                        {
                                minPos, maxPos
                        });
            }
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        CompoundNBT protectedAreas = new CompoundNBT();
        if (this.abilityProtections.size() > 0) {
            int i = 0;
            for (int[][] area : this.abilityProtections) {
                protectedAreas.putIntArray("minPos_" + i, area[0]);
                protectedAreas.putIntArray("maxPos_" + i, area[1]);
                i++;
            }
        }
        nbt.put("protectedAreas", protectedAreas);
        return nbt;
    }
}

