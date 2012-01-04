GCCloudGallery
==============

no external dependancies beyond Chute SDK

Description
-----------

This class allows for a gallery style view of a collection of GCAssets.  You can swipe back and forth to access the assets in order.  It also provides methods to jump to specific assets in the gallery and can switch to them in an animated (sliding to them) or inanimate (reloads at the new position) manner.

Subclassing
-----------

It is recommended to subclass this component however there are no methods that need to be overridden when subclassing.  The component will work without subclassing it.  If subclassed it is recommended that you override viewForPage if you wish to have a custom view for each GCAsset shown.  You can also override loadObjectsForCurrentPosition to allow custom behavior when switching pages, however it is recommended that you first call the superclass version of it or mimic it's behavior to maintain proper functionality of the gallery.  By subclassing you could use this class for other types of objects such as a gallery of GCChutes or GCParcels by overriding the viewForPage method, all other default implementations of the methods in this class are independent of the object type.

Initialization
--------------

*   images - NSArray of GCAssets - The GCAssets that the gallery will display.

Implementation
--------------

loading it with assets from a chute.

```objective-c
    GCCloudGallery *temp = [[GCCloudGallery alloc] init];
    [[[GCChute findById:@"34"] object] assetsInBackgroundWithCompletion:^(GCResponse *response){
        if([response isSuccessful]){
            NSArray *assets = [response object];
            [temp setObjects:assets];
        }
        [self presentModalViewController:temp animated:YES];
        [temp release];
    }];
```
