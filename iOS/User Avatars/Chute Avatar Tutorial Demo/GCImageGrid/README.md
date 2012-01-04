GCImageGrid
===========

no external dependancies beyond Chute SDK

Description
-----------

This class displays a wall of thumbnail images.  The size and spacing of the thumbnails is adjustable.  The default behavior when tapping a thumbnail is to display the image full screen.  The tap behavior can be adjusted by overriding the objectTappedAtIndex method.

Subclassing
-----------

It is recommended to subclass this component however there are no methods that need to be overridden when subclassing this component.  The component will work without subclassing it.

Initialization
--------------

* thumbSize (optional) - NSInteger - default value of 77
* spacingSize (optional) - NSInteger - default value of 2
* thumbCountPerRow (optional) - NSInteger - default depends on thumbSize and spacingSize.  Default is the max number of thumbs that will fit on a row.
* objects - NSArray of GCAssets

Implementation
--------------

Basic - uses defaults for thumbnail appearance.
   
```objective-c
    GCImageGrid *temp = [[GCImageGrid alloc] init];
    [[[GCChute findById:@"34"] object] assetsInBackgroundWithCompletion:^(GCResponse *response){
        if([response isSuccessful]){
            NSArray *assets = [response object];
            [temp setObjects:assets];
        }
        [self presentModalViewController:temp animated:YES];
        [temp release];
    }];
```

Advanced - customized look of thumbnails.

    
```objective-c
    GCImageGrid *temp = [[GCImageGrid alloc] init];
    [temp setSpacingSize:5];
    [temp setThumbSize:100];
    [temp setThumbCountPerRow:2];
    [[[GCChute findById:@"34"] object] assetsInBackgroundWithCompletion:^(GCResponse *response){
        if([response isSuccessful]){
            NSArray *assets = [response object];
            [temp setObjects:assets];
        }
        [self presentModalViewController:temp animated:YES];
        [temp release];
    }];
```