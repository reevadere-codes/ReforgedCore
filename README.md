# ReforgedCore
Testing a new block system for CR

## Life Cycle
The initialization and registration steps are handled through the Init class. Register your runnable/method lambda with the appropriate 'Stage'. Core will make sure they run at the appropriate time. This needs to be done in the @Mod classes constructor.


## Block Props
The 'Props' class mirrors the Vanilla Block.Properties builder with a few extra values to automate grouping/texturing/registering blocks.

Every Block that is created using a Props instance will share the same properties in it and will be grouped into a family, meaning:
 - they'll be shown together in the blockpalette gui
 - they can be represented by only one block from the family in the creative inventory

See the mod Block sources for the various constructor types that are supported. More can be added, but generally the `Block(Props)` type should serve any case:  
https://github.com/Conquest-Reforged/ReforgedCore/tree/master/src/main/java/com/conquestreforged/core/block


## Block Registration (Stage.BLOCK)
See how the Props class is used to register blocks here:  
https://github.com/Conquest-Reforged/ReforgedTestMod/blob/master/src/main/java/com/conquestreforged/test/init/ModBlock.java

A few notes:
- VanillaProps are a convenient way of populating the Props object from an existing Vanilla block type. This can be done manually by calling `Props.create()` and setting the various material properties instead.

- RegistryNames & blockstate/model/item/recipe json can be generated automatically if the Block class is annotated correctly. You should provide the plural and singular form of the base name for situations such as 'bricks' being the normal cube, but 'brick_stairs' being the stair variant. Texture overrides can be provided, either replacing all faces with one texture `Props.texture(texture:String)`, or by specifying each face `Props.texture(face:String, texture:String)`.

- No `public static Block` fields necessary. If reference to a specific Block is required this can be done via Forge's @ObjectHolder system.
