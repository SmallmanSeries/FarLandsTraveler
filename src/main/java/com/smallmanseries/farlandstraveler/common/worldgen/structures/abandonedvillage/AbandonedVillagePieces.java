package com.smallmanseries.farlandstraveler.common.worldgen.structures.abandonedvillage;

import com.google.common.collect.Lists;
import com.smallmanseries.farlandstraveler.common.worldgen.structures.FLTStructurePieceType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import javax.annotation.Nullable;
import java.util.List;

public class AbandonedVillagePieces {

    public static List<PieceWeight> getStructureVillageWeightedPieceList(RandomSource random, int size) {
        List<PieceWeight> list = Lists.newArrayList();
        list.add(new PieceWeight(Cottage.class, 4, Mth.nextInt(random, 2 + size, 4 + size * 2)));
        list.add(new PieceWeight(Church.class, 20, Mth.nextInt(random, size, 1 + size)));
        list.add(new PieceWeight(Library.class, 20, Mth.nextInt(random, size, 2 + size)));
        list.add(new PieceWeight(Hut.class, 3, Mth.nextInt(random, 2 + size, 5 + size * 3)));
        list.add(new PieceWeight(ButcherShop.class, 15, Mth.nextInt(random, size, 2 + size)));
        list.add(new PieceWeight(LargeFarm.class, 3, Mth.nextInt(random, 1 + size, 4 + size)));
        list.add(new PieceWeight(Farm.class, 3, Mth.nextInt(random, 2 + size, 4 + size * 2)));
        list.add(new PieceWeight(Smithy.class, 15, Mth.nextInt(random, 0, 1 + size)));
        list.add(new PieceWeight(House.class, 8, Mth.nextInt(random, size, 3 + size * 2)));

        list.removeIf(pieceWeight -> (pieceWeight).maxPlaceCount == 0);
        return list;
    }

    private static int updatePieceWeight(List<PieceWeight> weights) {
        boolean flag = false;
        int i = 0;
        for (PieceWeight pieceweight : weights) {
            if (pieceweight.maxPlaceCount > 0 && pieceweight.placeCount < pieceweight.maxPlaceCount) {
                flag = true;
            }
            i += pieceweight.weight;
        }
        return flag ? i : -1;
    }

    private static Piece createPieceFromClass(Start start, PieceWeight weight, StructurePieceAccessor structurePieces, RandomSource rand, int structureMinX, int structureMinY, int structureMinZ, Direction direction, int genDepth) {
        Class<? extends Piece> pieceClass = weight.pieceClass;

        if (pieceClass == Cottage.class) {
            return Cottage.createPiece(start, structurePieces, structureMinX, structureMinY, structureMinZ, direction, genDepth, rand);
        }
        if (pieceClass == Church.class) {
            return Church.createPiece(start, structurePieces, structureMinX, structureMinY, structureMinZ, direction, genDepth);
        }
        if (pieceClass == Library.class) {
            return Library.createPiece(start, structurePieces, structureMinX, structureMinY, structureMinZ, direction, genDepth);
        }
        if (pieceClass == Hut.class) {
            return Hut.createPiece(start, structurePieces, structureMinX, structureMinY, structureMinZ, direction, genDepth, rand);
        }
        if (pieceClass == ButcherShop.class) {
            return ButcherShop.createPiece(start, structurePieces, structureMinX, structureMinY, structureMinZ, direction, genDepth);
        }
        if (pieceClass == LargeFarm.class) {
            return LargeFarm.createPiece(start, structurePieces, structureMinX, structureMinY, structureMinZ, direction, genDepth);
        }
        if (pieceClass == Farm.class) {
            return Farm.createPiece(start, structurePieces, structureMinX, structureMinY, structureMinZ, direction, genDepth);
        }
        if (pieceClass == Smithy.class) {
            return Smithy.createPiece(start, structurePieces, structureMinX, structureMinY, structureMinZ, direction, genDepth);
        }
        if (pieceClass == House.class) {
            return House.createPiece(start, structurePieces, structureMinX, structureMinY, structureMinZ, direction, genDepth);
        }
        return null;
    }

    private static Piece generatePieces(Start start, StructurePieceAccessor structurePieces, RandomSource rand, int structureMinX, int structureMinY, int structureMinZ, Direction direction, int genDepth) {
        int i = updatePieceWeight(start.weightedPieceList);
        if (i <= 0) {
            return null;
        }
        for (int j = 0; j < 5; j++) {
            int k = rand.nextInt(i);
            for (PieceWeight pieceWeight : start.weightedPieceList) {
                k -= pieceWeight.weight;
                if (k < 0) {
                    if (pieceWeight.canSpawnMorePieces() || pieceWeight == start.pieceWeight && start.weightedPieceList.size() > 1) {
                        break;
                    }
                    Piece piece = createPieceFromClass(start, pieceWeight, structurePieces, rand, structureMinX, structureMinY, structureMinZ, direction, genDepth);
                    if (piece != null) {
                        ++pieceWeight.placeCount;
                        start.pieceWeight = pieceWeight;
                        if (pieceWeight.canSpawnMorePieces()) {
                            start.weightedPieceList.remove(pieceWeight);
                        }
                        return piece;
                    }
                }
            }
        }
        BoundingBox boundingBox = Streetlight.findPieceBox(structurePieces, structureMinX, structureMinY, structureMinZ, direction);
        if (boundingBox != null) {
            return new Streetlight(start, genDepth, boundingBox, direction);
        }
        return null;
    }

    private static StructurePiece generateAndAddPiece(Start start, StructurePieceAccessor structurePieces, RandomSource rand, int structureMinX, int structureMinY, int structureMinZ, Direction direction, int genDepth) {
        if (genDepth > 50) {
            return null;
        }
        if (Math.abs(structureMinX - start.getBoundingBox().minX()) <= 112 && Math.abs(structureMinZ - start.getBoundingBox().minZ()) <= 112) {
            StructurePiece structurePiece = generatePieces(start, structurePieces, rand, structureMinX, structureMinY, structureMinZ, direction, genDepth + 1);
            if (structurePiece != null) {
                structurePieces.addPiece(structurePiece);
                start.pendingHouses.add(structurePiece);
                return structurePiece;
            }
        }
        return null;
    }

    private static void generateAndAddRoadPiece(Start start, StructurePieceAccessor structurePieces, RandomSource rand, int structureMinX, int structureMinY, int structureMinZ, Direction direction, int genDepth) {
        if (genDepth > 3 + start.structureSize) {
            return;
        }
        if (Math.abs(structureMinX - start.getBoundingBox().minX()) <= 112 && Math.abs(structureMinZ - start.getBoundingBox().minZ()) <= 112) {
            BoundingBox boundingBox = Road.findPieceBox(structurePieces, rand, structureMinX, structureMinY, structureMinZ, direction);
            if (boundingBox != null && boundingBox.minY() > 10) {
                StructurePiece structurePiece = new Road(start, genDepth, boundingBox, direction);
                structurePieces.addPiece(structurePiece);
                start.pendingRoads.add(structurePiece);
            }
        }
    }

    public static class PieceWeight {
        public final Class<? extends Piece> pieceClass;
        public final int weight;
        public int placeCount;
        public final int maxPlaceCount;

        public PieceWeight(Class<? extends Piece> pieceClass, int weight, int maxPlaceCount) {
            this.pieceClass = pieceClass;
            this.weight = weight;
            this.maxPlaceCount = maxPlaceCount;
        }

        public boolean canSpawnMorePieces() {
            return this.maxPlaceCount != 0 && this.placeCount >= this.maxPlaceCount;
        }
    }

    public abstract static class Piece extends StructurePiece {
        protected int averageGroundLvl = -1;
        protected boolean isZombieInfested;
        protected AbandonedVillagePreset preset;

        protected Piece(Start start, StructurePieceType type, int genDepth, BoundingBox boundingBox) {
            super(type, genDepth, boundingBox);
            if (start != null) {
                this.isZombieInfested = start.isZombieInfested;
                this.preset = start.preset;
            }
        }

        protected Piece(StructurePieceType type, CompoundTag tag) {
            super(type, tag);
            this.averageGroundLvl = tag.getIntOr("GL", -1);
            this.isZombieInfested = tag.getBooleanOr("Z", false);
            this.preset = AbandonedVillagePreset.createDefault();
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            tag.putInt("GL", this.averageGroundLvl);
            tag.putBoolean("Z", this.isZombieInfested);
        }

        @Nullable
        protected StructurePiece getNextPiece(Start start, StructurePieceAccessor structurePieces, RandomSource rand, int yShift, int xzShift) {
            Direction direction = this.getOrientation();
            if (direction != null) {
                return switch (direction) {
                    case WEST, EAST ->
                            AbandonedVillagePieces.generateAndAddPiece(start, structurePieces, rand, this.boundingBox.minX() + xzShift, this.boundingBox.minY() + yShift, this.boundingBox.minZ() - 1, Direction.NORTH, this.getGenDepth());
                    default ->
                            AbandonedVillagePieces.generateAndAddPiece(start, structurePieces, rand, this.boundingBox.minX() - 1, this.boundingBox.minY() + yShift, this.boundingBox.minZ() + xzShift, Direction.WEST, this.getGenDepth());
                };
            }
            return null;
        }

        @Nullable
        protected StructurePiece getNextPieceOtherSide(Start start, StructurePieceAccessor structurePieces, RandomSource rand, int yShift, int xzShift) {
            Direction direction = this.getOrientation();
            if (direction != null) {
                return switch (direction) {
                    case WEST, EAST ->
                            AbandonedVillagePieces.generateAndAddPiece(start, structurePieces, rand, this.boundingBox.minX() + xzShift, this.boundingBox.minY() + yShift, this.boundingBox.maxZ() + 1, Direction.SOUTH, this.getGenDepth());
                    default ->
                            AbandonedVillagePieces.generateAndAddPiece(start, structurePieces, rand, this.boundingBox.maxX() + 1, this.boundingBox.minY() + yShift, this.boundingBox.minZ() + xzShift, Direction.EAST, this.getGenDepth());
                };
            }
            return null;
        }

        protected int getAverageGroundLevel(WorldGenLevel level, BoundingBox boundingBox) {
            int i = 0, j = 0;
            BlockPos.MutableBlockPos blockpos$mutableBlockPos = new BlockPos.MutableBlockPos();
            for (int k = this.boundingBox.minZ(); k <= this.boundingBox.maxZ(); ++k) {
                for (int l = this.boundingBox.minX(); l <= this.boundingBox.maxX(); ++l) {
                    blockpos$mutableBlockPos.set(l, 64, k);
                    if (boundingBox.isInside(blockpos$mutableBlockPos)) {
                        i += Math.max(level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockpos$mutableBlockPos).getY(), level.getSeaLevel());
                        ++j;
                    }
                }
            }
            if (j == 0) {
                return -1;
            }
            return i / j;
        }

        protected static boolean canVillageGoDeeper(BoundingBox boundingBox) {
            return boundingBox != null && boundingBox.minY() > 10;
        }

        protected BlockState getSpecificBlockState(BlockState blockState) {
            if (blockState.is(Blocks.COBBLESTONE)) {
                return this.preset.blockCobble();
            }
            if (blockState.is(Blocks.OAK_STAIRS)) {
                return this.preset.blockStairsWood();
            }
            if (blockState.is(Blocks.OAK_PLANKS)) {
                return this.preset.blockPlanks();
            }
            if (blockState.is(Blocks.COBBLESTONE_STAIRS)) {
                return this.preset.blockStairsCobble();
            }
            if (blockState.is(Blocks.OAK_LOG)) {
                return this.preset.blockLog();
            }
            if (blockState.is(Blocks.OAK_FENCE)) {
                return this.preset.blockFence();
            }
            if (blockState.is(Blocks.GRAVEL)) {
                return this.preset.blockGravel();
            }
            if (blockState.is(Blocks.OAK_DOOR)) {
                return this.preset.blockDoor();
            }
            if (blockState.is(Blocks.OAK_PRESSURE_PLATE)) {
                return this.preset.blockPressurePlate();
            }
            if (blockState.is(Blocks.LADDER)) {
                return this.preset.blockLadder();
            }
            if (blockState.is(Blocks.BLACKSTONE)) {
                return this.preset.blockCobble2();
            }
            // 未来需要添加特定方块的时候可以直接加到这里，后面所有选取方块的过程都被这个getSpecificBlockState包装过（除了流体）
            return blockState;
        }

        protected void generateDoor(WorldGenLevel level, BoundingBox boundingBox, int x, int y, int z, Direction direction) {
            if (!this.isZombieInfested) {
                this.placeBlock(level, this.getSpecificBlockState(Blocks.OAK_DOOR.defaultBlockState()).setValue(DoorBlock.FACING, direction), x, y, z, boundingBox);
                this.placeBlock(level, this.getSpecificBlockState(Blocks.OAK_DOOR.defaultBlockState()).setValue(DoorBlock.FACING, direction).setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER), x, y + 1, z, boundingBox);
            }
        }

        protected void generateTorch(WorldGenLevel level, Direction direction, int x, int y, int z, BoundingBox boundingBox) {
            if (!this.isZombieInfested) {
                this.placeBlock(level, this.getSpecificBlockState(Blocks.WALL_TORCH.defaultBlockState()).setValue(WallTorchBlock.FACING, direction), x, y, z, boundingBox);
            }
        }

        protected void replaceAirAndLiquidDownwards(WorldGenLevel level, BlockState blockState, int x, int y, int z, BoundingBox boundingBox) {
            BlockState specificBlockState = this.getSpecificBlockState(blockState);
            super.fillColumnDown(level, specificBlockState, x, y, z, boundingBox);
        }
    }

    public static class Well extends Piece {

        public Well(Start start, int genDepth, RandomSource rand, int x, int z) {
            super(start, FLTStructurePieceType.ABANDONED_VILLAGE_WELL.get(), genDepth, BoundingBox.infinite());
            this.setOrientation(Direction.Plane.HORIZONTAL.getRandomDirection(rand));
            this.boundingBox = new BoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
        }

        public Well(CompoundTag tag) {
            super(FLTStructurePieceType.ABANDONED_VILLAGE_WELL.get(), tag);
        }

        public void addChildren(StructurePiece piece, StructurePieceAccessor pieces, RandomSource rand) {
            AbandonedVillagePieces.generateAndAddRoadPiece((Start) piece, pieces, rand, this.boundingBox.minX() - 1, this.boundingBox.maxY() - 4, this.boundingBox.minZ() + 1, Direction.WEST, this.getGenDepth());
            AbandonedVillagePieces.generateAndAddRoadPiece((Start) piece, pieces, rand, this.boundingBox.maxX() + 1, this.boundingBox.maxY() - 4, this.boundingBox.minZ() + 1, Direction.EAST, this.getGenDepth());
            AbandonedVillagePieces.generateAndAddRoadPiece((Start) piece, pieces, rand, this.boundingBox.minX() + 1, this.boundingBox.maxY() - 4, this.boundingBox.minZ() - 1, Direction.NORTH, this.getGenDepth());
            AbandonedVillagePieces.generateAndAddRoadPiece((Start) piece, pieces, rand, this.boundingBox.minX() + 1, this.boundingBox.maxY() - 4, this.boundingBox.maxZ() + 1, Direction.SOUTH, this.getGenDepth());
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.averageGroundLvl < 0) {
                this.averageGroundLvl = this.getAverageGroundLevel(level, boundingBox);
                if (this.averageGroundLvl < 0) {
                    return;
                }
                this.move(0, this.averageGroundLvl - this.boundingBox.maxY() + 3, 0);
            }

            BlockState blockCobble = this.getSpecificBlockState(Blocks.COBBLESTONE.defaultBlockState());
            BlockState blockFence = this.getSpecificBlockState(Blocks.OAK_FENCE.defaultBlockState());
            this.generateBox(level, boundingBox, 1, 0, 1, 4, 12, 4, blockCobble, Blocks.WATER.defaultBlockState(), false);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 2, 12, 2, boundingBox);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 3, 12, 2, boundingBox);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 2, 12, 3, boundingBox);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 3, 12, 3, boundingBox);
            this.generateAirBox(level, boundingBox, 1, 13, 1, 4, 14, 4);
            this.placeBlock(level, blockFence, 1, 13, 1, boundingBox);
            this.placeBlock(level, blockFence, 1, 14, 1, boundingBox);
            this.placeBlock(level, blockFence, 4, 13, 1, boundingBox);
            this.placeBlock(level, blockFence, 4, 14, 1, boundingBox);
            this.placeBlock(level, blockFence, 1, 13, 4, boundingBox);
            this.placeBlock(level, blockFence, 1, 14, 4, boundingBox);
            this.placeBlock(level, blockFence, 4, 13, 4, boundingBox);
            this.placeBlock(level, blockFence, 4, 14, 4, boundingBox);
            this.generateBox(level, boundingBox, 1, 15, 1, 4, 15, 4, blockCobble, blockCobble, false);

            for (int i = 0; i <= 5; ++i) {
                for (int j = 0; j <= 5; ++j) {
                    if (j == 0 || j == 5 || i == 0 || i == 5) {
                        this.placeBlock(level, blockCobble, j, 11, i, boundingBox);
                    }
                }
            }
        }
    }

    public static class Start extends Well {
        public int structureSize;
        public PieceWeight pieceWeight;
        public List<PieceWeight> weightedPieceList;
        public List<StructurePiece> pendingHouses = Lists.newArrayList();
        public List<StructurePiece> pendingRoads = Lists.newArrayList();

        public Start(AbandonedVillagePreset blocks, RandomSource rand, int x, int z, List<PieceWeight> pieceWeights, int structureSize) {
            super(null, 0, rand, x, z);
            this.weightedPieceList = pieceWeights;
            this.structureSize = structureSize;
            this.preset = blocks;
            this.isZombieInfested = rand.nextInt(50) == 0;
        }
    }

    public static class Hut extends Piece {
        private final boolean isTallHouse;
        private final int tablePosition;

        public Hut(Start start, int genDepth, BoundingBox boundingBox, Direction direction, RandomSource rand) {
            super(start, FLTStructurePieceType.ABANDONED_VILLAGE_HUT.get(), genDepth, boundingBox);
            this.setOrientation(direction);
            this.isTallHouse = rand.nextBoolean();
            this.tablePosition = rand.nextInt(3);
        }

        public Hut(CompoundTag tag) {
            super(FLTStructurePieceType.ABANDONED_VILLAGE_HUT.get(), tag);
            this.tablePosition = tag.getIntOr("P", 0);
            this.isTallHouse = tag.getBooleanOr("T", false);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            tag.putInt("P", this.tablePosition);
            tag.putBoolean("T", this.isTallHouse);
        }

        public static Hut createPiece(Start start, StructurePieceAccessor pieces, int structureMinX, int structureMinY, int structureMinZ, Direction direction, int genDepth, RandomSource rand) {
            BoundingBox boundingbox = BoundingBox.orientBox(structureMinX, structureMinY, structureMinZ, 0, 0, 0, 4, 6, 5, direction);
            return canVillageGoDeeper(boundingbox) && pieces.findCollisionPiece(boundingbox) == null ? new Hut(start, genDepth, boundingbox, direction, rand) : null;
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.averageGroundLvl < 0) {
                this.averageGroundLvl = this.getAverageGroundLevel(level, boundingBox);
                if (this.averageGroundLvl < 0) {
                    return;
                }
                this.move(0, this.averageGroundLvl - this.boundingBox.maxY() + 6 - 1, 0);
            }

            BlockState blockCobble = this.getSpecificBlockState(Blocks.COBBLESTONE.defaultBlockState());
            BlockState blockPlanks = this.getSpecificBlockState(Blocks.OAK_PLANKS.defaultBlockState());
            BlockState blockStairs = this.getSpecificBlockState(Blocks.COBBLESTONE_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.NORTH);
            BlockState blockLog = this.getSpecificBlockState(Blocks.OAK_LOG.defaultBlockState());
            BlockState blockFence = this.getSpecificBlockState(Blocks.OAK_FENCE.defaultBlockState());
            this.generateAirBox(level, boundingBox, 1, 1, 1, 2, 4, 3);
            this.generateBox(level, boundingBox, 0, 0, 0, 3, 0, 4, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 1, 0, 1, 2, 0, 3, this.getSpecificBlockState(Blocks.DIRT.defaultBlockState()), this.getSpecificBlockState(Blocks.DIRT.defaultBlockState()), false);

            if (this.isTallHouse) {
                this.generateBox(level, boundingBox, 1, 4, 1, 2, 4, 3, blockLog, blockLog, false);
            } else {
                this.generateBox(level, boundingBox, 1, 5, 1, 2, 5, 3, blockLog, blockLog, false);
            }

            BlockState blockGlassSN = this.getSpecificBlockState(Blocks.GLASS_PANE.defaultBlockState()).setValue(IronBarsBlock.NORTH, true).setValue(IronBarsBlock.SOUTH, true);
            this.placeBlock(level, blockLog, 1, 4, 0, boundingBox);
            this.placeBlock(level, blockLog, 2, 4, 0, boundingBox);
            this.placeBlock(level, blockLog, 1, 4, 4, boundingBox);
            this.placeBlock(level, blockLog, 2, 4, 4, boundingBox);
            this.placeBlock(level, blockLog, 0, 4, 1, boundingBox);
            this.placeBlock(level, blockLog, 0, 4, 2, boundingBox);
            this.placeBlock(level, blockLog, 0, 4, 3, boundingBox);
            this.placeBlock(level, blockLog, 3, 4, 1, boundingBox);
            this.placeBlock(level, blockLog, 3, 4, 2, boundingBox);
            this.placeBlock(level, blockLog, 3, 4, 3, boundingBox);
            this.generateBox(level, boundingBox, 0, 1, 0, 0, 3, 0, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 3, 1, 0, 3, 3, 0, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 0, 1, 4, 0, 3, 4, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 3, 1, 4, 3, 3, 4, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 0, 1, 1, 0, 3, 3, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 3, 1, 1, 3, 3, 3, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 1, 1, 0, 2, 3, 0, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 1, 1, 4, 2, 3, 4, blockPlanks, blockPlanks, false);
            this.placeBlock(level, blockGlassSN, 0, 2, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 3, 2, 2, boundingBox);

            if (this.tablePosition > 0) {
                this.placeBlock(level, blockFence, this.tablePosition, 1, 3, boundingBox);
                this.placeBlock(level, this.getSpecificBlockState(Blocks.OAK_PRESSURE_PLATE.defaultBlockState()), this.tablePosition, 2, 3, boundingBox);
            }

            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 1, 1, 0, boundingBox);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 1, 2, 0, boundingBox);
            this.generateDoor(level, boundingBox, 1, 1, 0, Direction.NORTH);

            if (this.getBlock(level, 1, 0, -1, boundingBox).isAir() && !this.getBlock(level, 1, -1, -1, boundingBox).isAir()) {
                this.placeBlock(level, blockStairs, 1, 0, -1, boundingBox);
            }

            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < 4; ++j) {
                    this.replaceAirAndLiquidDownwards(level, blockCobble, j, -1, i, boundingBox);
                }
            }
        }
    }

    public static class Cottage extends Piece {
        private final boolean isRoofAccessible;

        public Cottage(Start start, int genDepth, BoundingBox boundingBox, Direction direction, RandomSource rand) {
            super(start, FLTStructurePieceType.ABANDONED_VILLAGE_COTTAGE.get(), genDepth, boundingBox);
            this.setOrientation(direction);
            this.isRoofAccessible = rand.nextBoolean();
        }

        public Cottage(CompoundTag tag) {
            super(FLTStructurePieceType.ABANDONED_VILLAGE_COTTAGE.get(), tag);
            this.isRoofAccessible = tag.getBooleanOr("G", false);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            tag.putBoolean("G", this.isRoofAccessible);
        }

        public static Cottage createPiece(Start start, StructurePieceAccessor pieces, int structureMinX, int structureMinY, int structureMinZ, Direction direction, int genDepth, RandomSource rand) {
            BoundingBox boundingBox = BoundingBox.orientBox(structureMinX, structureMinY, structureMinZ, 0, 0, 0, 5, 6, 5, direction);
            return pieces.findCollisionPiece(boundingBox) != null ? null : new Cottage(start, genDepth, boundingBox, direction, rand);
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.averageGroundLvl < 0) {
                this.averageGroundLvl = this.getAverageGroundLevel(level, boundingBox);
                if (this.averageGroundLvl < 0) {
                    return;
                }
                this.move(0, this.averageGroundLvl - this.boundingBox.maxY() + 6 - 1, 0);
            }

            BlockState blockCobble = this.getSpecificBlockState(Blocks.COBBLESTONE.defaultBlockState());
            BlockState blockPlanks = this.getSpecificBlockState(Blocks.OAK_PLANKS.defaultBlockState());
            BlockState blockStairs = this.getSpecificBlockState(Blocks.COBBLESTONE_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.NORTH);
            BlockState blockLog = this.getSpecificBlockState(Blocks.OAK_LOG.defaultBlockState());
            BlockState blockFence = this.getSpecificBlockState(Blocks.OAK_FENCE.defaultBlockState());
            BlockState blockGlassSN = this.getSpecificBlockState(Blocks.GLASS_PANE.defaultBlockState()).setValue(IronBarsBlock.NORTH, true).setValue(IronBarsBlock.SOUTH, true);
            BlockState blockGlassEW = this.getSpecificBlockState(Blocks.GLASS_PANE.defaultBlockState()).setValue(IronBarsBlock.EAST, true).setValue(IronBarsBlock.WEST, true);
            this.generateBox(level, boundingBox, 0, 0, 0, 4, 0, 4, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 0, 4, 0, 4, 4, 4, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 1, 4, 1, 3, 4, 3, blockPlanks, blockPlanks, false);
            this.placeBlock(level, blockCobble, 0, 1, 0, boundingBox);
            this.placeBlock(level, blockCobble, 0, 2, 0, boundingBox);
            this.placeBlock(level, blockCobble, 0, 3, 0, boundingBox);
            this.placeBlock(level, blockCobble, 4, 1, 0, boundingBox);
            this.placeBlock(level, blockCobble, 4, 2, 0, boundingBox);
            this.placeBlock(level, blockCobble, 4, 3, 0, boundingBox);
            this.placeBlock(level, blockCobble, 0, 1, 4, boundingBox);
            this.placeBlock(level, blockCobble, 0, 2, 4, boundingBox);
            this.placeBlock(level, blockCobble, 0, 3, 4, boundingBox);
            this.placeBlock(level, blockCobble, 4, 1, 4, boundingBox);
            this.placeBlock(level, blockCobble, 4, 2, 4, boundingBox);
            this.placeBlock(level, blockCobble, 4, 3, 4, boundingBox);
            this.generateBox(level, boundingBox, 0, 1, 1, 0, 3, 3, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 4, 1, 1, 4, 3, 3, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 1, 1, 4, 3, 3, 4, blockPlanks, blockPlanks, false);
            this.placeBlock(level, blockGlassSN, 0, 2, 2, boundingBox);
            this.placeBlock(level, blockGlassEW, 2, 2, 4, boundingBox);
            this.placeBlock(level, blockGlassSN, 4, 2, 2, boundingBox);
            this.placeBlock(level, blockPlanks, 1, 1, 0, boundingBox);
            this.placeBlock(level, blockPlanks, 1, 2, 0, boundingBox);
            this.placeBlock(level, blockPlanks, 1, 3, 0, boundingBox);
            this.placeBlock(level, blockPlanks, 2, 3, 0, boundingBox);
            this.placeBlock(level, blockPlanks, 3, 3, 0, boundingBox);
            this.placeBlock(level, blockPlanks, 3, 2, 0, boundingBox);
            this.placeBlock(level, blockPlanks, 3, 1, 0, boundingBox);

            if (this.getBlock(level, 2, 0, -1, boundingBox).isAir() && !this.getBlock(level, 2, -1, -1, boundingBox).isAir()) {
                this.placeBlock(level, blockStairs, 2, 0, -1, boundingBox);
            }

            this.generateAirBox(level, boundingBox, 1, 1, 1, 3, 3, 3);

            if (this.isRoofAccessible) {
                this.generateAirBox(level, boundingBox, 0, 5, 0, 4, 8, 4);
                this.placeBlock(level, blockFence, 0, 5, 0, boundingBox);
                this.placeBlock(level, blockFence, 1, 5, 0, boundingBox);
                this.placeBlock(level, blockFence, 2, 5, 0, boundingBox);
                this.placeBlock(level, blockFence, 3, 5, 0, boundingBox);
                this.placeBlock(level, blockFence, 4, 5, 0, boundingBox);
                this.placeBlock(level, blockFence, 0, 5, 4, boundingBox);
                this.placeBlock(level, blockFence, 1, 5, 4, boundingBox);
                this.placeBlock(level, blockFence, 2, 5, 4, boundingBox);
                this.placeBlock(level, blockFence, 3, 5, 4, boundingBox);
                this.placeBlock(level, blockFence, 4, 5, 4, boundingBox);
                this.placeBlock(level, blockFence, 4, 5, 1, boundingBox);
                this.placeBlock(level, blockFence, 4, 5, 2, boundingBox);
                this.placeBlock(level, blockFence, 4, 5, 3, boundingBox);
                this.placeBlock(level, blockFence, 0, 5, 1, boundingBox);
                this.placeBlock(level, blockFence, 0, 5, 2, boundingBox);
                this.placeBlock(level, blockFence, 0, 5, 3, boundingBox);

                BlockState blockLadder = this.getSpecificBlockState(Blocks.LADDER.defaultBlockState()).setValue(LadderBlock.FACING, Direction.SOUTH);
                this.placeBlock(level, blockLadder, 3, 1, 3, boundingBox);
                this.placeBlock(level, blockLadder, 3, 2, 3, boundingBox);
                this.placeBlock(level, blockLadder, 3, 3, 3, boundingBox);
                this.placeBlock(level, blockLadder, 3, 4, 3, boundingBox);
            }
            this.generateTorch(level, Direction.NORTH, 2, 3, 1, boundingBox);

            for (int j = 0; j < 5; ++j) {
                for (int i = 0; i < 5; ++i) {
                    this.replaceAirAndLiquidDownwards(level, blockCobble, i, -1, j, boundingBox);
                }
            }
        }
    }

    public static class House extends Piece {

        public House(Start start, int genDepth, BoundingBox boundingBox, Direction direction) {
            super(start, FLTStructurePieceType.ABANDONED_VILLAGE_HOUSE.get(), genDepth, boundingBox);
            this.setOrientation(direction);
        }

        public House(CompoundTag tag) {
            super(FLTStructurePieceType.ABANDONED_VILLAGE_HOUSE.get(), tag);
        }

        public static House createPiece(Start start, StructurePieceAccessor pieces, int structureMinX, int structureMinY, int structureMinZ, Direction direction, int genDepth) {
            BoundingBox structureboundingbox = BoundingBox.orientBox(structureMinX, structureMinY, structureMinZ, 0, 0, 0, 9, 7, 12, direction);
            return canVillageGoDeeper(structureboundingbox) && pieces.findCollisionPiece(structureboundingbox) == null ? new House(start, genDepth, structureboundingbox, direction) : null;
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.averageGroundLvl < 0) {
                this.averageGroundLvl = this.getAverageGroundLevel(level, boundingBox);
                if (this.averageGroundLvl < 0) {
                    return;
                }
                this.move(0, this.averageGroundLvl - this.boundingBox.maxY() + 7 - 1, 0);
            }

            BlockState blockCobble = this.getSpecificBlockState(Blocks.COBBLESTONE.defaultBlockState());
            BlockState blockStairsNorth = this.getSpecificBlockState(Blocks.OAK_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.NORTH);
            BlockState blockStairsSouth = this.getSpecificBlockState(Blocks.OAK_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.SOUTH);
            BlockState blockStairsEast = this.getSpecificBlockState(Blocks.OAK_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.EAST);
            BlockState blockStairsWest = this.getSpecificBlockState(Blocks.OAK_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.WEST);
            BlockState blockPlanks = this.getSpecificBlockState(Blocks.OAK_PLANKS.defaultBlockState());
            BlockState blockLog = this.getSpecificBlockState(Blocks.OAK_LOG.defaultBlockState());
            this.generateAirBox(level, boundingBox, 1, 1, 1, 7, 4, 4);
            this.generateAirBox(level, boundingBox, 3, 1, 5, 7, 4, 9);
            this.generateAirBox(level, boundingBox, 5, 5, 4, 5, 5, 9);
            this.generateBox(level, boundingBox, 2, 0, 5, 8, 0, 10, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 1, 0, 1, 7, 0, 4, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 0, 0, 0, 0, 3, 5, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 8, 0, 0, 8, 3, 10, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 1, 0, 0, 7, 2, 0, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 1, 0, 5, 2, 1, 5, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 2, 0, 6, 2, 3, 10, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 3, 0, 10, 7, 3, 10, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 1, 2, 0, 7, 3, 0, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 1, 2, 5, 2, 3, 5, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 0, 4, 1, 8, 4, 1, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 0, 4, 4, 3, 4, 4, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 0, 5, 2, 8, 5, 3, blockPlanks, blockPlanks, false);
            this.placeBlock(level, blockPlanks, 0, 4, 2, boundingBox);
            this.placeBlock(level, blockPlanks, 0, 4, 3, boundingBox);
            this.placeBlock(level, blockPlanks, 8, 4, 2, boundingBox);
            this.placeBlock(level, blockPlanks, 8, 4, 3, boundingBox);
            this.placeBlock(level, blockPlanks, 8, 4, 4, boundingBox);

            for (int i = -1; i <= 2; ++i) {
                for (int j = 0; j <= 8; ++j) {
                    this.placeBlock(level, blockStairsNorth, j, 4 + i, i, boundingBox);
                    if ((i > -1 || j <= 1) && (i > 0 || j <= 3) && (i > 1 || j != 5)) {
                        this.placeBlock(level, blockStairsSouth, j, 4 + i, 5 - i, boundingBox);
                    }
                }
            }

            this.generateBox(level, boundingBox, 3, 4, 5, 3, 4, 10, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 7, 4, 2, 7, 4, 10, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 4, 5, 4, 4, 5, 10, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 6, 5, 4, 6, 5, 10, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 5, 6, 3, 5, 6, 10, blockPlanks, blockPlanks, false);

            for (int k = 4; k >= 1; --k) {
                this.placeBlock(level, blockPlanks, k, 2 + k, 7 - k, boundingBox);
                for (int k1 = 8 - k; k1 <= 10; ++k1) {
                    this.placeBlock(level, blockStairsEast, k, 2 + k, k1, boundingBox);
                }
            }

            this.placeBlock(level, blockPlanks, 6, 6, 3, boundingBox);
            this.placeBlock(level, blockPlanks, 7, 5, 4, boundingBox);
            this.placeBlock(level, blockStairsWest, 6, 6, 4, boundingBox);

            for (int l = 6; l <= 8; ++l) {
                for (int l1 = 5; l1 <= 10; ++l1) {
                    this.placeBlock(level, blockStairsWest, l, 12 - l, l1, boundingBox);
                }
            }

            BlockState blockGlassSN = this.getSpecificBlockState(Blocks.GLASS_PANE.defaultBlockState()).setValue(IronBarsBlock.NORTH, true).setValue(IronBarsBlock.SOUTH, true);
            BlockState blockGlassEW = this.getSpecificBlockState(Blocks.GLASS_PANE.defaultBlockState()).setValue(IronBarsBlock.EAST, true).setValue(IronBarsBlock.WEST, true);
            this.placeBlock(level, blockLog, 0, 2, 1, boundingBox);
            this.placeBlock(level, blockLog, 0, 2, 4, boundingBox);
            this.placeBlock(level, blockGlassSN, 0, 2, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 0, 2, 3, boundingBox);
            this.placeBlock(level, blockLog, 4, 2, 0, boundingBox);
            this.placeBlock(level, blockGlassEW, 5, 2, 0, boundingBox);
            this.placeBlock(level, blockLog, 6, 2, 0, boundingBox);
            this.placeBlock(level, blockLog, 8, 2, 1, boundingBox);
            this.placeBlock(level, blockGlassSN, 8, 2, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 8, 2, 3, boundingBox);
            this.placeBlock(level, blockLog, 8, 2, 4, boundingBox);
            this.placeBlock(level, blockPlanks, 8, 2, 5, boundingBox);
            this.placeBlock(level, blockLog, 8, 2, 6, boundingBox);
            this.placeBlock(level, blockGlassSN, 8, 2, 7, boundingBox);
            this.placeBlock(level, blockGlassSN, 8, 2, 8, boundingBox);
            this.placeBlock(level, blockLog, 8, 2, 9, boundingBox);
            this.placeBlock(level, blockLog, 2, 2, 6, boundingBox);
            this.placeBlock(level, blockGlassSN, 2, 2, 7, boundingBox);
            this.placeBlock(level, blockGlassSN, 2, 2, 8, boundingBox);
            this.placeBlock(level, blockLog, 2, 2, 9, boundingBox);
            this.placeBlock(level, blockLog, 4, 4, 10, boundingBox);
            this.placeBlock(level, blockGlassEW, 5, 4, 10, boundingBox);
            this.placeBlock(level, blockLog, 6, 4, 10, boundingBox);
            this.placeBlock(level, blockPlanks, 5, 5, 10, boundingBox);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 2, 1, 0, boundingBox);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 2, 2, 0, boundingBox);
            this.generateTorch(level, Direction.NORTH, 2, 3, 1, boundingBox);
            this.generateDoor(level, boundingBox, 2, 1, 0, Direction.NORTH);
            this.generateAirBox(level, boundingBox, 1, 0, -1, 3, 2, -1);

            if (this.getBlock(level, 2, 0, -1, boundingBox).isAir() && !this.getBlock(level, 2, -1, -1, boundingBox).isAir()) {
                this.placeBlock(level, blockStairsNorth, 2, 0, -1, boundingBox);
            }

            for (int i1 = 0; i1 < 5; ++i1) {
                for (int i2 = 0; i2 < 9; ++i2) {
                    this.replaceAirAndLiquidDownwards(level, blockCobble, i2, -1, i1, boundingBox);
                }
            }

            for (int j1 = 5; j1 < 11; ++j1) {
                for (int j2 = 2; j2 < 9; ++j2) {
                    this.replaceAirAndLiquidDownwards(level, blockCobble, j2, -1, j1, boundingBox);
                }
            }
        }
    }

    public static class ButcherShop extends Piece {

        public ButcherShop(Start start, int genDepth, BoundingBox boundingBox, Direction direction) {
            super(start, FLTStructurePieceType.ABANDONED_VILLAGE_BUTCHER_SHOP.get(), genDepth, boundingBox);
            this.setOrientation(direction);
        }

        public ButcherShop(CompoundTag tag) {
            super(FLTStructurePieceType.ABANDONED_VILLAGE_BUTCHER_SHOP.get(), tag);
        }

        public static ButcherShop createPiece(Start start, StructurePieceAccessor pieces, int structureMinX, int structureMinY, int structureMinZ, Direction direction, int genDepth) {
            BoundingBox boundingBox = BoundingBox.orientBox(structureMinX, structureMinY, structureMinZ, 0, 0, 0, 9, 7, 11, direction);
            return canVillageGoDeeper(boundingBox) && pieces.findCollisionPiece(boundingBox) == null ? new ButcherShop(start, genDepth, boundingBox, direction) : null;
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.averageGroundLvl < 0) {
                this.averageGroundLvl = this.getAverageGroundLevel(level, boundingBox);
                if (this.averageGroundLvl < 0) {
                    return;
                }
                this.move(0, this.averageGroundLvl - this.boundingBox.maxY() + 7 - 1, 0);
            }

            BlockState blockCobble = this.getSpecificBlockState(Blocks.COBBLESTONE.defaultBlockState());
            BlockState blockStairsNorth = this.getSpecificBlockState(Blocks.OAK_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.NORTH);
            BlockState blockStairsSouth = this.getSpecificBlockState(Blocks.OAK_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.SOUTH);
            BlockState blockStairsWest = this.getSpecificBlockState(Blocks.OAK_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.WEST);
            BlockState blockPlanks = this.getSpecificBlockState(Blocks.OAK_PLANKS.defaultBlockState());
            BlockState blockLog = this.getSpecificBlockState(Blocks.OAK_LOG.defaultBlockState());
            BlockState blockFence = this.getSpecificBlockState(Blocks.OAK_FENCE.defaultBlockState());
            BlockState blockSlab = this.getSpecificBlockState(Blocks.SMOOTH_STONE_SLAB.defaultBlockState());
            this.generateAirBox(level, boundingBox, 1, 1, 1, 7, 4, 4);
            this.generateAirBox(level, boundingBox, 2, 1, 6, 8, 4, 10);
            this.generateBox(level, boundingBox, 2, 0, 6, 8, 0, 10, this.getSpecificBlockState(Blocks.DIRT.defaultBlockState()), this.getSpecificBlockState(Blocks.DIRT.defaultBlockState()), false);
            this.placeBlock(level, blockCobble, 6, 0, 6, boundingBox);
            this.generateBox(level, boundingBox, 2, 1, 6, 2, 1, 10, blockFence, blockFence, false);
            this.generateBox(level, boundingBox, 8, 1, 6, 8, 1, 10, blockFence, blockFence, false);
            this.generateBox(level, boundingBox, 3, 1, 10, 7, 1, 10, blockFence, blockFence, false);
            this.generateBox(level, boundingBox, 1, 0, 1, 7, 0, 4, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 0, 0, 0, 0, 3, 5, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 8, 0, 0, 8, 3, 5, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 1, 0, 0, 7, 1, 0, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 1, 0, 5, 7, 1, 5, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 1, 2, 0, 7, 3, 0, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 1, 2, 5, 7, 3, 5, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 0, 4, 1, 8, 4, 1, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 0, 4, 4, 8, 4, 4, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 0, 5, 2, 8, 5, 3, blockPlanks, blockPlanks, false);
            this.placeBlock(level, blockPlanks, 0, 4, 2, boundingBox);
            this.placeBlock(level, blockPlanks, 0, 4, 3, boundingBox);
            this.placeBlock(level, blockPlanks, 8, 4, 2, boundingBox);
            this.placeBlock(level, blockPlanks, 8, 4, 3, boundingBox);

            for (int i = -1; i <= 2; ++i) {
                for (int j = 0; j <= 8; ++j) {
                    this.placeBlock(level, blockStairsNorth, j, 4 + i, i, boundingBox);
                    this.placeBlock(level, blockStairsSouth, j, 4 + i, 5 - i, boundingBox);
                }
            }

            BlockState blockGlassSN = this.getSpecificBlockState(Blocks.GLASS_PANE.defaultBlockState()).setValue(IronBarsBlock.NORTH, true).setValue(IronBarsBlock.SOUTH, true);
            BlockState blockGlassEW = this.getSpecificBlockState(Blocks.GLASS_PANE.defaultBlockState()).setValue(IronBarsBlock.EAST, true).setValue(IronBarsBlock.WEST, true);
            this.placeBlock(level, blockLog, 0, 2, 1, boundingBox);
            this.placeBlock(level, blockLog, 0, 2, 4, boundingBox);
            this.placeBlock(level, blockLog, 8, 2, 1, boundingBox);
            this.placeBlock(level, blockLog, 8, 2, 4, boundingBox);
            this.placeBlock(level, blockGlassSN, 0, 2, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 0, 2, 3, boundingBox);
            this.placeBlock(level, blockGlassSN, 8, 2, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 8, 2, 3, boundingBox);
            this.placeBlock(level, blockGlassEW, 2, 2, 5, boundingBox);
            this.placeBlock(level, blockGlassEW, 3, 2, 5, boundingBox);
            this.placeBlock(level, blockGlassEW, 5, 2, 0, boundingBox);
            this.placeBlock(level, blockFence, 2, 1, 3, boundingBox);
            this.placeBlock(level, this.getSpecificBlockState(Blocks.OAK_PRESSURE_PLATE.defaultBlockState()), 2, 2, 3, boundingBox);
            this.placeBlock(level, blockPlanks, 1, 1, 4, boundingBox);
            this.placeBlock(level, blockStairsNorth, 2, 1, 4, boundingBox);
            this.placeBlock(level, blockStairsWest, 1, 1, 3, boundingBox);
            this.generateBox(level, boundingBox, 5, 0, 1, 7, 0, 3, blockSlab.setValue(SlabBlock.TYPE, SlabType.DOUBLE), blockSlab.setValue(SlabBlock.TYPE, SlabType.DOUBLE), false);
            this.placeBlock(level, blockSlab.setValue(SlabBlock.TYPE, SlabType.DOUBLE), 6, 1, 1, boundingBox);
            this.placeBlock(level, blockSlab.setValue(SlabBlock.TYPE, SlabType.DOUBLE), 6, 1, 2, boundingBox);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 2, 1, 0, boundingBox);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 2, 2, 0, boundingBox);
            this.generateTorch(level, Direction.NORTH, 2, 3, 1, boundingBox);
            this.generateDoor(level, boundingBox, 2, 1, 0, Direction.NORTH);

            if (this.getBlock(level, 2, 0, -1, boundingBox).isAir() && !this.getBlock(level, 2, -1, -1, boundingBox).isAir()) {
                this.placeBlock(level, blockStairsNorth, 2, 0, -1, boundingBox);
            }

            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 6, 1, 5, boundingBox);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 6, 2, 5, boundingBox);
            this.generateTorch(level, Direction.SOUTH, 6, 3, 4, boundingBox);
            this.generateDoor(level, boundingBox, 6, 1, 5, Direction.SOUTH);

            for (int k = 0; k < 6; ++k) {
                for (int l = 0; l < 9; ++l) {
                    this.replaceAirAndLiquidDownwards(level, blockCobble, l, -1, k, boundingBox);
                }
            }
        }
    }

    public static class Library extends Piece {

        public Library(Start start, int genDepth, BoundingBox boundingBox, Direction direction) {
            super(start, FLTStructurePieceType.ABANDONED_VILLAGE_LIBRARY.get(), genDepth, boundingBox);
            this.setOrientation(direction);
        }

        public Library(CompoundTag tag) {
            super(FLTStructurePieceType.ABANDONED_VILLAGE_LIBRARY.get(), tag);
        }

        public static Library createPiece(Start start, StructurePieceAccessor pieces, int structureMinX, int structureMinY, int structureMinZ, Direction direction, int genDepth) {
            BoundingBox structureboundingbox = BoundingBox.orientBox(structureMinX, structureMinY, structureMinZ, 0, 0, 0, 9, 9, 6, direction);
            return canVillageGoDeeper(structureboundingbox) && pieces.findCollisionPiece(structureboundingbox) == null ? new Library(start, genDepth, structureboundingbox, direction) : null;
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.averageGroundLvl < 0) {
                this.averageGroundLvl = this.getAverageGroundLevel(level, boundingBox);
                if (this.averageGroundLvl < 0) {
                    return;
                }
                this.move(0, this.averageGroundLvl - this.boundingBox.maxY() + 9 - 1, 0);
            }

            BlockState blockCobble = this.getSpecificBlockState(Blocks.COBBLESTONE.defaultBlockState());
            BlockState blockStairsNorth = this.getSpecificBlockState(Blocks.OAK_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.NORTH);
            BlockState blockStairsSouth = this.getSpecificBlockState(Blocks.OAK_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.SOUTH);
            BlockState blockStairsEast = this.getSpecificBlockState(Blocks.OAK_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.EAST);
            BlockState blockPlanks = this.getSpecificBlockState(Blocks.OAK_PLANKS.defaultBlockState());
            BlockState blockStairsStone = this.getSpecificBlockState(Blocks.COBBLESTONE_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.NORTH);
            BlockState blockFence = this.getSpecificBlockState(Blocks.OAK_FENCE.defaultBlockState());
            this.generateAirBox(level, boundingBox, 1, 1, 1, 7, 5, 4);
            this.generateBox(level, boundingBox, 0, 0, 0, 8, 0, 5, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 0, 5, 0, 8, 5, 5, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 0, 6, 1, 8, 6, 4, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 0, 7, 2, 8, 7, 3, blockCobble, blockCobble, false);

            for (int i = -1; i <= 2; ++i) {
                for (int j = 0; j <= 8; ++j) {
                    this.placeBlock(level, blockStairsNorth, j, 6 + i, i, boundingBox);
                    this.placeBlock(level, blockStairsSouth, j, 6 + i, 5 - i, boundingBox);
                }
            }

            BlockState blockGlassSN = this.getSpecificBlockState(Blocks.GLASS_PANE.defaultBlockState()).setValue(IronBarsBlock.NORTH, true).setValue(IronBarsBlock.SOUTH, true);
            BlockState blockGlassEW = this.getSpecificBlockState(Blocks.GLASS_PANE.defaultBlockState()).setValue(IronBarsBlock.EAST, true).setValue(IronBarsBlock.WEST, true);
            this.generateBox(level, boundingBox, 0, 1, 0, 0, 1, 5, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 1, 1, 5, 8, 1, 5, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 8, 1, 0, 8, 1, 4, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 2, 1, 0, 7, 1, 0, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 0, 2, 0, 0, 4, 0, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 0, 2, 5, 0, 4, 5, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 8, 2, 5, 8, 4, 5, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 8, 2, 0, 8, 4, 0, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 0, 2, 1, 0, 4, 4, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 1, 2, 5, 7, 4, 5, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 8, 2, 1, 8, 4, 4, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 1, 2, 0, 7, 4, 0, blockPlanks, blockPlanks, false);
            this.placeBlock(level, blockGlassEW, 4, 2, 0, boundingBox);
            this.placeBlock(level, blockGlassEW, 5, 2, 0, boundingBox);
            this.placeBlock(level, blockGlassEW, 6, 2, 0, boundingBox);
            this.placeBlock(level, blockGlassEW, 4, 3, 0, boundingBox);
            this.placeBlock(level, blockGlassEW, 5, 3, 0, boundingBox);
            this.placeBlock(level, blockGlassEW, 6, 3, 0, boundingBox);
            this.placeBlock(level, blockGlassSN, 0, 2, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 0, 2, 3, boundingBox);
            this.placeBlock(level, blockGlassSN, 0, 3, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 0, 3, 3, boundingBox);
            this.placeBlock(level, blockGlassSN, 8, 2, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 8, 2, 3, boundingBox);
            this.placeBlock(level, blockGlassSN, 8, 3, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 8, 3, 3, boundingBox);
            this.placeBlock(level, blockGlassEW, 2, 2, 5, boundingBox);
            this.placeBlock(level, blockGlassEW, 3, 2, 5, boundingBox);
            this.placeBlock(level, blockGlassEW, 5, 2, 5, boundingBox);
            this.placeBlock(level, blockGlassEW, 6, 2, 5, boundingBox);
            this.generateBox(level, boundingBox, 1, 4, 1, 7, 4, 1, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 1, 4, 4, 7, 4, 4, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 1, 3, 4, 7, 3, 4, this.getSpecificBlockState(Blocks.BOOKSHELF.defaultBlockState()), this.getSpecificBlockState(Blocks.BOOKSHELF.defaultBlockState()), false);
            this.placeBlock(level, blockPlanks, 7, 1, 4, boundingBox);
            this.placeBlock(level, blockStairsEast, 7, 1, 3, boundingBox);
            this.placeBlock(level, blockStairsNorth, 6, 1, 4, boundingBox);
            this.placeBlock(level, blockStairsNorth, 5, 1, 4, boundingBox);
            this.placeBlock(level, blockStairsNorth, 4, 1, 4, boundingBox);
            this.placeBlock(level, blockStairsNorth, 3, 1, 4, boundingBox);
            this.placeBlock(level, blockFence, 6, 1, 3, boundingBox);
            this.placeBlock(level, this.getSpecificBlockState(Blocks.OAK_PRESSURE_PLATE.defaultBlockState()), 6, 2, 3, boundingBox);
            this.placeBlock(level, blockFence, 4, 1, 3, boundingBox);
            this.placeBlock(level, this.getSpecificBlockState(Blocks.OAK_PRESSURE_PLATE.defaultBlockState()), 4, 2, 3, boundingBox);
            this.placeBlock(level, this.getSpecificBlockState(Blocks.CRAFTING_TABLE.defaultBlockState()), 7, 1, 1, boundingBox);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 1, 1, 0, boundingBox);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 1, 2, 0, boundingBox);
            this.generateDoor(level, boundingBox, 1, 1, 0, Direction.NORTH);

            if (this.getBlock(level, 1, 0, -1, boundingBox).isAir() && !this.getBlock(level, 1, -1, -1, boundingBox).isAir()) {
                this.placeBlock(level, blockStairsStone, 1, 0, -1, boundingBox);
            }

            for (int l = 0; l < 6; ++l) {
                for (int k = 0; k < 9; ++k) {
                    this.replaceAirAndLiquidDownwards(level, blockCobble, k, -1, l, boundingBox);
                }
            }
        }
    }

    public static class LargeFarm extends Piece {

        public LargeFarm(Start start, int genDepth, BoundingBox boundingBox, Direction direction) {
            super(start, FLTStructurePieceType.ABANDONED_VILLAGE_LARGE_FARM.get(), genDepth, boundingBox);
            this.setOrientation(direction);
        }

        public LargeFarm(CompoundTag tag) {
            super(FLTStructurePieceType.ABANDONED_VILLAGE_LARGE_FARM.get(), tag);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
        }

        public static LargeFarm createPiece(Start start, StructurePieceAccessor pieces, int structureMinX, int structureMinY, int structureMinZ, Direction direction, int genDepth) {
            BoundingBox boundingBox = BoundingBox.orientBox(structureMinX, structureMinY, structureMinZ, 0, 0, 0, 13, 4, 9, direction);
            return canVillageGoDeeper(boundingBox) && pieces.findCollisionPiece(boundingBox) == null ? new LargeFarm(start, genDepth, boundingBox, direction) : null;
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.averageGroundLvl < 0) {
                this.averageGroundLvl = this.getAverageGroundLevel(level, boundingBox);
                if (this.averageGroundLvl < 0) {
                    return;
                }
                this.move(0, this.averageGroundLvl - this.boundingBox.maxY() + 4 - 1, 0);
            }

            BlockState blockLog = this.getSpecificBlockState(Blocks.OAK_LOG.defaultBlockState());
            BlockState blockFarm = this.getSpecificBlockState(Blocks.FARMLAND.defaultBlockState()).setValue(FarmBlock.MOISTURE, 7);
            BlockState blockDirt = this.getSpecificBlockState(Blocks.DIRT.defaultBlockState());
            this.generateAirBox(level, boundingBox, 0, 1, 0, 12, 4, 8);
            this.generateBox(level, boundingBox, 1, 0, 1, 2, 0, 7, blockFarm, blockFarm, false);
            this.generateBox(level, boundingBox, 4, 0, 1, 5, 0, 7, blockFarm, blockFarm, false);
            this.generateBox(level, boundingBox, 7, 0, 1, 8, 0, 7, blockFarm, blockFarm, false);
            this.generateBox(level, boundingBox, 10, 0, 1, 11, 0, 7, blockFarm, blockFarm, false);
            this.generateBox(level, boundingBox, 0, 0, 0, 0, 0, 8, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 6, 0, 0, 6, 0, 8, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 12, 0, 0, 12, 0, 8, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 1, 0, 0, 11, 0, 0, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 1, 0, 8, 11, 0, 8, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 3, 0, 1, 3, 0, 7, Blocks.WATER.defaultBlockState(), Blocks.WATER.defaultBlockState(), false);
            this.generateBox(level, boundingBox, 9, 0, 1, 9, 0, 7, Blocks.WATER.defaultBlockState(), Blocks.WATER.defaultBlockState(), false);

            for (int j2 = 0; j2 < 9; ++j2) {
                for (int k2 = 0; k2 < 13; ++k2) {
                    this.replaceAirAndLiquidDownwards(level, blockDirt, k2, -1, j2, boundingBox);
                }
            }
        }
    }

    public static class Farm extends Piece {

        public Farm(Start start, int genDepth, BoundingBox boundingBox, Direction direction) {
            super(start, FLTStructurePieceType.ABANDONED_VILLAGE_FARM.get(), genDepth, boundingBox);
            this.setOrientation(direction);
        }

        public Farm(CompoundTag tag) {
            super(FLTStructurePieceType.ABANDONED_VILLAGE_FARM.get(), tag);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
        }

        public static Farm createPiece(Start start, StructurePieceAccessor pieces, int structureMinX, int structureMinY, int structureMinZ, Direction direction, int genDepth) {
            BoundingBox boundingBox = BoundingBox.orientBox(structureMinX, structureMinY, structureMinZ, 0, 0, 0, 7, 4, 9, direction);
            return canVillageGoDeeper(boundingBox) && pieces.findCollisionPiece(boundingBox) == null ? new Farm(start, genDepth, boundingBox, direction) : null;
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.averageGroundLvl < 0) {
                this.averageGroundLvl = this.getAverageGroundLevel(level, boundingBox);
                if (this.averageGroundLvl < 0) {
                    return;
                }
                this.move(0, this.averageGroundLvl - this.boundingBox.maxY() + 4 - 1, 0);
            }

            BlockState blockLog = this.getSpecificBlockState(Blocks.OAK_LOG.defaultBlockState());
            BlockState blockFarm = this.getSpecificBlockState(Blocks.FARMLAND.defaultBlockState()).setValue(FarmBlock.MOISTURE, 7);
            BlockState blockDirt = this.getSpecificBlockState(Blocks.DIRT.defaultBlockState());
            this.generateAirBox(level, boundingBox, 0, 1, 0, 6, 4, 8);
            this.generateBox(level, boundingBox, 1, 0, 1, 2, 0, 7, blockFarm, blockFarm, false);
            this.generateBox(level, boundingBox, 4, 0, 1, 5, 0, 7, blockFarm, blockFarm, false);
            this.generateBox(level, boundingBox, 0, 0, 0, 0, 0, 8, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 6, 0, 0, 6, 0, 8, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 1, 0, 0, 5, 0, 0, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 1, 0, 8, 5, 0, 8, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 3, 0, 1, 3, 0, 7, Blocks.WATER.defaultBlockState(), Blocks.WATER.defaultBlockState(), false);

            for (int j1 = 0; j1 < 9; ++j1) {
                for (int k1 = 0; k1 < 7; ++k1) {
                    this.replaceAirAndLiquidDownwards(level, blockDirt, k1, -1, j1, boundingBox);
                }
            }
        }
    }

    public static class Smithy extends Piece {
        private boolean hasMadeChest;

        public Smithy(Start start, int genDepth, BoundingBox boundingBox, Direction direction) {
            super(start, FLTStructurePieceType.ABANDONED_VILLAGE_SMITHY.get(), genDepth, boundingBox);
            this.setOrientation(direction);
        }

        public Smithy(CompoundTag tag) {
            super(FLTStructurePieceType.ABANDONED_VILLAGE_SMITHY.get(), tag);
            this.hasMadeChest = tag.getBooleanOr("C", false);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            tag.putBoolean("C", this.hasMadeChest);
        }

        public static Smithy createPiece(Start start, StructurePieceAccessor pieces, int structureMinX, int structureMinY, int structureMinZ, Direction direction, int genDepth) {
            BoundingBox boundingBox = BoundingBox.orientBox(structureMinX, structureMinY, structureMinZ, 0, 0, 0, 10, 6, 7, direction);
            return canVillageGoDeeper(boundingBox) && pieces.findCollisionPiece(boundingBox) == null ? new Smithy(start, genDepth, boundingBox, direction) : null;
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.averageGroundLvl < 0) {
                this.averageGroundLvl = this.getAverageGroundLevel(level, boundingBox);
                if (this.averageGroundLvl < 0) {
                    return;
                }
                this.move(0, this.averageGroundLvl - this.boundingBox.maxY() + 6 - 1, 0);
            }

            BlockState blockCobble = this.getSpecificBlockState(Blocks.BLACKSTONE.defaultBlockState());
            BlockState blockStairsNorth = this.getSpecificBlockState(Blocks.OAK_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.NORTH);
            BlockState blockStairsWest = this.getSpecificBlockState(Blocks.OAK_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.WEST);
            BlockState blockPlanks = this.getSpecificBlockState(Blocks.OAK_PLANKS.defaultBlockState());
            BlockState blockStairsStone = this.getSpecificBlockState(Blocks.COBBLESTONE_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.NORTH);
            BlockState blockLog = this.getSpecificBlockState(Blocks.OAK_LOG.defaultBlockState());
            BlockState blockFence = this.getSpecificBlockState(Blocks.OAK_FENCE.defaultBlockState());
            BlockState blockGlassSN = this.getSpecificBlockState(Blocks.GLASS_PANE.defaultBlockState()).setValue(IronBarsBlock.NORTH, true).setValue(IronBarsBlock.SOUTH, true);
            BlockState blockGlassEW = this.getSpecificBlockState(Blocks.GLASS_PANE.defaultBlockState()).setValue(IronBarsBlock.EAST, true).setValue(IronBarsBlock.WEST, true);
            this.generateAirBox(level, boundingBox, 1, 1, 1, 9, 3, 6);
            this.generateBox(level, boundingBox, 0, 0, 0, 9, 0, 6, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 0, 4, 0, 9, 4, 6, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 0, 5, 0, 9, 5, 6, this.getSpecificBlockState(Blocks.SMOOTH_STONE_SLAB.defaultBlockState()), this.getSpecificBlockState(Blocks.SMOOTH_STONE_SLAB.defaultBlockState()), false);
            this.generateAirBox(level, boundingBox, 1, 5, 1, 8, 5, 5);
            this.generateBox(level, boundingBox, 1, 1, 0, 2, 3, 0, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 0, 1, 0, 0, 4, 0, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 3, 1, 0, 3, 4, 0, blockLog, blockLog, false);
            this.generateBox(level, boundingBox, 0, 1, 6, 0, 4, 6, blockLog, blockLog, false);
            this.placeBlock(level, blockPlanks, 3, 3, 1, boundingBox);
            this.generateBox(level, boundingBox, 3, 1, 2, 3, 3, 2, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 4, 1, 3, 5, 3, 3, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 0, 1, 1, 0, 3, 5, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 1, 1, 6, 5, 3, 6, blockPlanks, blockPlanks, false);
            this.generateBox(level, boundingBox, 5, 1, 0, 5, 3, 0, blockFence, blockFence, false);
            this.generateBox(level, boundingBox, 9, 1, 0, 9, 3, 0, blockFence, blockFence, false);
            this.generateBox(level, boundingBox, 6, 1, 4, 9, 4, 6, blockCobble, blockCobble, false);
            this.placeBlock(level, Blocks.LAVA.defaultBlockState(), 7, 1, 5, boundingBox);
            this.placeBlock(level, Blocks.LAVA.defaultBlockState(), 8, 1, 5, boundingBox);
            this.placeBlock(level, this.getSpecificBlockState(Blocks.IRON_BARS.defaultBlockState()).setValue(IronBarsBlock.NORTH, true).setValue(IronBarsBlock.SOUTH, true), 9, 2, 5, boundingBox);
            this.placeBlock(level, this.getSpecificBlockState(Blocks.IRON_BARS.defaultBlockState()).setValue(IronBarsBlock.NORTH, true), 9, 2, 4, boundingBox);
            this.generateAirBox(level, boundingBox, 7, 2, 4, 8, 2, 5);
            this.placeBlock(level, blockCobble, 6, 1, 3, boundingBox);
            this.placeBlock(level, this.getSpecificBlockState(Blocks.FURNACE.defaultBlockState()).setValue(FurnaceBlock.FACING, Direction.SOUTH), 6, 2, 3, boundingBox);
            this.placeBlock(level, this.getSpecificBlockState(Blocks.FURNACE.defaultBlockState()).setValue(FurnaceBlock.FACING, Direction.SOUTH), 6, 3, 3, boundingBox);
            this.placeBlock(level, this.getSpecificBlockState(Blocks.SMOOTH_STONE_SLAB.defaultBlockState()).setValue(SlabBlock.TYPE, SlabType.DOUBLE), 8, 1, 1, boundingBox);
            this.placeBlock(level, blockGlassSN, 0, 2, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 0, 2, 4, boundingBox);
            this.placeBlock(level, blockGlassEW, 2, 2, 6, boundingBox);
            this.placeBlock(level, blockGlassEW, 4, 2, 6, boundingBox);
            this.placeBlock(level, blockFence, 2, 1, 4, boundingBox);
            this.placeBlock(level, this.getSpecificBlockState(Blocks.OAK_PRESSURE_PLATE.defaultBlockState()), 2, 2, 4, boundingBox);
            this.placeBlock(level, blockPlanks, 1, 1, 5, boundingBox);
            this.placeBlock(level, blockStairsNorth, 2, 1, 5, boundingBox);
            this.placeBlock(level, blockStairsWest, 1, 1, 4, boundingBox);

            if (!this.hasMadeChest && boundingBox.isInside(new BlockPos(this.getWorldX(5, 5), this.getWorldY(1), this.getWorldZ(5, 5)))) {
                this.hasMadeChest = true;
                this.createChest(level, boundingBox, rand, 5, 1, 5, BuiltInLootTables.VILLAGE_TOOLSMITH);
            }

            for (int i = 6; i <= 8; ++i) {
                if (this.getBlock(level, i, 0, -1, boundingBox).isAir() && !this.getBlock(level, i, -1, -1, boundingBox).isAir()) {
                    this.placeBlock(level, blockStairsStone, i, 0, -1, boundingBox);
                }
            }

            for (int k = 0; k < 7; ++k) {
                for (int j = 0; j < 10; ++j) {
                    this.replaceAirAndLiquidDownwards(level, blockCobble, j, -1, k, boundingBox);
                }
            }
        }
    }

    public static class Church extends Piece {

        public Church(Start start, int genDepth, BoundingBox boundingBox, Direction direction) {
            super(start, FLTStructurePieceType.ABANDONED_VILLAGE_CHURCH.get(), genDepth, boundingBox);
            this.setOrientation(direction);
        }

        public Church(CompoundTag tag) {
            super(FLTStructurePieceType.ABANDONED_VILLAGE_CHURCH.get(), tag);
        }

        public static Church createPiece(Start start, StructurePieceAccessor pieces, int structureMinX, int structureMinY, int structureMinZ, Direction direction, int genDepth) {
            BoundingBox boundingBox = BoundingBox.orientBox(structureMinX, structureMinY, structureMinZ, 0, 0, 0, 5, 12, 9, direction);
            return canVillageGoDeeper(boundingBox) && pieces.findCollisionPiece(boundingBox) == null ? new Church(start, genDepth, boundingBox, direction) : null;
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.averageGroundLvl < 0) {
                this.averageGroundLvl = this.getAverageGroundLevel(level, boundingBox);
                if (this.averageGroundLvl < 0) {
                    return;
                }
                this.move(0, this.averageGroundLvl - this.boundingBox.maxY() + 12 - 1, 0);
            }

            BlockState blockCobble = this.getSpecificBlockState(Blocks.BLACKSTONE.defaultBlockState());
            BlockState blockStairsNorth = this.getSpecificBlockState(Blocks.COBBLESTONE_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.NORTH);
            BlockState blockStairsWest = this.getSpecificBlockState(Blocks.COBBLESTONE_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.WEST);
            BlockState blockStairsEast = this.getSpecificBlockState(Blocks.COBBLESTONE_STAIRS.defaultBlockState()).setValue(StairBlock.FACING, Direction.EAST);
            BlockState blockGlassSN = this.getSpecificBlockState(Blocks.GLASS_PANE.defaultBlockState()).setValue(IronBarsBlock.NORTH, true).setValue(IronBarsBlock.SOUTH, true);
            BlockState blockGlassEW = this.getSpecificBlockState(Blocks.GLASS_PANE.defaultBlockState()).setValue(IronBarsBlock.EAST, true).setValue(IronBarsBlock.WEST, true);
            this.generateAirBox(level, boundingBox, 1, 1, 1, 3, 3, 7);
            this.generateAirBox(level, boundingBox, 1, 5, 1, 3, 9, 3);
            this.generateBox(level, boundingBox, 1, 0, 0, 3, 0, 8, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 1, 1, 0, 3, 10, 0, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 0, 1, 1, 0, 10, 3, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 4, 1, 1, 4, 10, 3, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 0, 0, 4, 0, 4, 7, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 4, 0, 4, 4, 4, 7, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 1, 1, 8, 3, 4, 8, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 1, 5, 4, 3, 10, 4, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 1, 5, 5, 3, 5, 7, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 0, 9, 0, 4, 9, 4, blockCobble, blockCobble, false);
            this.generateBox(level, boundingBox, 0, 4, 0, 4, 4, 4, blockCobble, blockCobble, false);
            this.placeBlock(level, blockCobble, 0, 11, 2, boundingBox);
            this.placeBlock(level, blockCobble, 4, 11, 2, boundingBox);
            this.placeBlock(level, blockCobble, 2, 11, 0, boundingBox);
            this.placeBlock(level, blockCobble, 2, 11, 4, boundingBox);
            this.placeBlock(level, blockCobble, 1, 1, 6, boundingBox);
            this.placeBlock(level, blockCobble, 1, 1, 7, boundingBox);
            this.placeBlock(level, blockCobble, 2, 1, 7, boundingBox);
            this.placeBlock(level, blockCobble, 3, 1, 6, boundingBox);
            this.placeBlock(level, blockCobble, 3, 1, 7, boundingBox);
            this.placeBlock(level, blockStairsNorth, 1, 1, 5, boundingBox);
            this.placeBlock(level, blockStairsNorth, 2, 1, 6, boundingBox);
            this.placeBlock(level, blockStairsNorth, 3, 1, 5, boundingBox);
            this.placeBlock(level, blockStairsWest, 1, 2, 7, boundingBox);
            this.placeBlock(level, blockStairsEast, 3, 2, 7, boundingBox);
            this.placeBlock(level, blockGlassSN, 0, 2, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 0, 3, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 4, 2, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 4, 3, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 0, 6, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 0, 7, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 4, 6, 2, boundingBox);
            this.placeBlock(level, blockGlassSN, 4, 7, 2, boundingBox);
            this.placeBlock(level, blockGlassEW, 2, 6, 0, boundingBox);
            this.placeBlock(level, blockGlassEW, 2, 7, 0, boundingBox);
            this.placeBlock(level, blockGlassEW, 2, 6, 4, boundingBox);
            this.placeBlock(level, blockGlassEW, 2, 7, 4, boundingBox);
            this.placeBlock(level, blockGlassSN, 0, 3, 6, boundingBox);
            this.placeBlock(level, blockGlassSN, 4, 3, 6, boundingBox);
            this.placeBlock(level, blockGlassEW, 2, 3, 8, boundingBox);
            this.generateAirBox(level, boundingBox, 1, 4, 5, 3, 4, 7);
            this.generateTorch(level, Direction.SOUTH, 2, 4, 7, boundingBox);
            this.generateTorch(level, Direction.EAST, 1, 4, 6, boundingBox);
            this.generateTorch(level, Direction.WEST, 3, 4, 6, boundingBox);
            this.generateTorch(level, Direction.NORTH, 2, 4, 5, boundingBox);
            BlockState blockLadder = this.getSpecificBlockState(Blocks.LADDER.defaultBlockState()).setValue(LadderBlock.FACING, Direction.WEST);

            for (int i = 1; i <= 9; ++i) {
                this.placeBlock(level, blockLadder, 3, i, 3, boundingBox);
            }

            this.generateAirBox(level, boundingBox, 1, 10, 1, 3, 12, 3);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 2, 1, 0, boundingBox);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 2, 2, 0, boundingBox);
            this.generateDoor(level, boundingBox, 2, 1, 0, Direction.NORTH);

            if (this.getBlock(level, 2, 0, -1, boundingBox).isAir() && !this.getBlock(level, 2, -1, -1, boundingBox).isAir()) {
                this.placeBlock(level, blockStairsNorth, 2, 0, -1, boundingBox);
            }

            for (int k = 0; k < 9; ++k) {
                for (int j = 0; j < 5; ++j) {
                    this.replaceAirAndLiquidDownwards(level, blockCobble, j, -1, k, boundingBox);
                }
            }
        }
    }

    public static class Streetlight extends Piece {

        public Streetlight(Start start, int genDepth, BoundingBox boundingBox, Direction direction) {
            super(start, FLTStructurePieceType.ABANDONED_VILLAGE_STREETLIGHT.get(), genDepth, boundingBox);
            this.setOrientation(direction);
        }

        public Streetlight(CompoundTag tag) {
            super(FLTStructurePieceType.ABANDONED_VILLAGE_STREETLIGHT.get(), tag);
        }

        public static BoundingBox findPieceBox(StructurePieceAccessor pieces, int structureMinX, int structureMinY, int structureMinZ, Direction direction) {
            BoundingBox boundingBox = BoundingBox.orientBox(structureMinX, structureMinY, structureMinZ, 0, 0, 0, 3, 4, 2, direction);
            return pieces.findCollisionPiece(boundingBox) != null ? null : boundingBox;
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.averageGroundLvl < 0) {
                this.averageGroundLvl = this.getAverageGroundLevel(level, boundingBox);
                if (this.averageGroundLvl < 0) {
                    return;
                }
                this.move(0, this.averageGroundLvl - this.boundingBox.maxY() + 4 - 1, 0);
            }

            BlockState blockFence = this.getSpecificBlockState(Blocks.OAK_FENCE.defaultBlockState());
            this.generateAirBox(level, boundingBox, 0, 0, 0, 2, 3, 1);
            this.placeBlock(level, blockFence, 1, 0, 0, boundingBox);
            this.placeBlock(level, blockFence, 1, 1, 0, boundingBox);
            this.placeBlock(level, blockFence, 1, 2, 0, boundingBox);
            this.placeBlock(level, this.getSpecificBlockState(Blocks.BLACK_WOOL.defaultBlockState()), 1, 3, 0, boundingBox);
            this.generateTorch(level, Direction.EAST, 2, 3, 0, boundingBox);
            this.generateTorch(level, Direction.NORTH, 1, 3, 1, boundingBox);
            this.generateTorch(level, Direction.WEST, 0, 3, 0, boundingBox);
            this.generateTorch(level, Direction.SOUTH, 1, 3, -1, boundingBox);
        }
    }

    public static class Road extends Piece {
        private final int length;

        public Road(Start start, int genDepth, BoundingBox boundingBox, Direction direction) {
            super(start, FLTStructurePieceType.ABANDONED_VILLAGE_ROAD.get(), genDepth, boundingBox);
            this.setOrientation(direction);
            this.length = Math.max(boundingBox.getXSpan(), boundingBox.getZSpan());
        }

        public Road(CompoundTag tag) {
            super(FLTStructurePieceType.ABANDONED_VILLAGE_ROAD.get(), tag);
            this.length = tag.getIntOr("L", 0);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            tag.putInt("L", this.length);
        }

        public void addChildren(StructurePiece piece, StructurePieceAccessor pieces, RandomSource rand) {
            boolean flag = false;
            for (int i = rand.nextInt(5); i < this.length - 8; i += 2 + rand.nextInt(5)) {
                StructurePiece piece1 = this.getNextPiece((Start) piece, pieces, rand, 0, i);
                if (piece1 != null) {
                    i += Math.max(piece1.getBoundingBox().getXSpan(), piece1.getBoundingBox().getZSpan());
                    flag = true;
                }
            }
            for (int j = rand.nextInt(5); j < this.length - 8; j += 2 + rand.nextInt(5)) {
                StructurePiece piece2 = this.getNextPieceOtherSide((Start) piece, pieces, rand, 0, j);
                if (piece2 != null) {
                    j += Math.max(piece2.getBoundingBox().getXSpan(), piece2.getBoundingBox().getZSpan());
                    flag = true;
                }
            }

            Direction direction = this.getOrientation();
            if (flag && rand.nextInt(3) > 0 && direction != null) {
                switch (direction) {
                    case SOUTH ->
                            AbandonedVillagePieces.generateAndAddRoadPiece((Start) piece, pieces, rand, this.boundingBox.minX() - 1, this.boundingBox.minY(), this.boundingBox.maxZ() - 2, Direction.WEST, this.getGenDepth());
                    case WEST ->
                            AbandonedVillagePieces.generateAndAddRoadPiece((Start) piece, pieces, rand, this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ() - 1, Direction.NORTH, this.getGenDepth());
                    case EAST ->
                            AbandonedVillagePieces.generateAndAddRoadPiece((Start) piece, pieces, rand, this.boundingBox.maxX() - 2, this.boundingBox.minY(), this.boundingBox.minZ() - 1, Direction.NORTH, this.getGenDepth());
                    default ->
                            AbandonedVillagePieces.generateAndAddRoadPiece((Start) piece, pieces, rand, this.boundingBox.minX() - 1, this.boundingBox.minY(), this.boundingBox.minZ(), Direction.WEST, this.getGenDepth());
                }
            }

            if (flag && rand.nextInt(3) > 0 && direction != null) {
                switch (direction) {
                    case SOUTH ->
                            AbandonedVillagePieces.generateAndAddRoadPiece((Start) piece, pieces, rand, this.boundingBox.maxX() + 1, this.boundingBox.minY(), this.boundingBox.maxZ() - 2, Direction.EAST, this.getGenDepth());
                    case WEST ->
                            AbandonedVillagePieces.generateAndAddRoadPiece((Start) piece, pieces, rand, this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.maxZ() + 1, Direction.SOUTH, this.getGenDepth());
                    case EAST ->
                            AbandonedVillagePieces.generateAndAddRoadPiece((Start) piece, pieces, rand, this.boundingBox.maxX() - 2, this.boundingBox.minY(), this.boundingBox.maxZ() + 1, Direction.SOUTH, this.getGenDepth());
                    default ->
                            AbandonedVillagePieces.generateAndAddRoadPiece((Start) piece, pieces, rand, this.boundingBox.maxX() + 1, this.boundingBox.minY(), this.boundingBox.minZ(), Direction.EAST, this.getGenDepth());
                }
            }
        }

        public static BoundingBox findPieceBox(StructurePieceAccessor pieces, RandomSource rand, int structureMinX, int structureMinY, int structureMinZ, Direction direction) {
            for (int i = 7 * Mth.nextInt(rand, 3, 5); i >= 7; i -= 7) {
                BoundingBox boundingBox = BoundingBox.orientBox(structureMinX, structureMinY, structureMinZ, 0, 0, 0, 3, 3, i, direction);
                if (pieces.findCollisionPiece(boundingBox) == null) {
                    return boundingBox;
                }
            }
            return null;
        }

        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            BlockState blockCobble = this.getSpecificBlockState(Blocks.COBBLESTONE.defaultBlockState());
            BlockState blockPlanks = this.getSpecificBlockState(Blocks.OAK_PLANKS.defaultBlockState());
            BlockState blockGravel = this.getSpecificBlockState(Blocks.GRAVEL.defaultBlockState());

            for (int i = this.boundingBox.minX(); i <= this.boundingBox.maxX(); ++i) {
                for (int j = this.boundingBox.minZ(); j <= this.boundingBox.maxZ(); ++j) {
                    BlockPos blockpos = new BlockPos(i, 64, j);
                    if (boundingBox.isInside(blockpos)) {
                        blockpos = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockpos).below();
                        while (blockpos.getY() >= level.getSeaLevel() - 1) {
                            BlockState blockStateTemp = level.getBlockState(blockpos);
                            if (blockStateTemp.getFluidState().is(FluidTags.WATER)) {
                                level.setBlock(blockpos, blockPlanks, 2);
                                break;
                            }
                            if (blockStateTemp.isCollisionShapeFullBlock(EmptyBlockGetter.INSTANCE, BlockPos.ZERO)) {
                                level.setBlock(blockpos, blockGravel, 2);
                                level.setBlock(blockpos.below(), blockCobble, 2);
                                break;
                            }
                            blockpos = blockpos.below();
                        }
                    }
                }
            }
        }
    }
}