GCMultiImagePicker
==================

no external dependancies beyond Chute SDK

Description
-----------

This class allows for the selection of multiple GCAssets from a wall of thumbnails.  You may provide a NSArray of GCAssets to display.  If you don't provide an array it will automatically load all images saved on the device.  The size of the thumbnails and spacing between the thumbnails can be assigned before displaying the view.  You may also set the number of images per row if you prefer a custom number.  If you don't set this value it will automatically calculate the max number of images that will fit on a row given the thumb and spacing size as well as the width of the table that is displaying them.  Regardless of the number of images set, it will center each row in the table.  It will allow you to have images past the edge of the view so keep this in mind when setting this value.  You can also set a custom image to display over a selected image.  This can be done either programmatically or in interface builder by setting the selectedIndicator to a UIView with the image you want to use.  Keep in mind that the image will be the size of the full image, so you likely will want to have some transparent spaces in the overlaid image to see the selected image through.  The array of selected GCAssets can be returned by calling selectedImages.  While this component can be used as is, it is recommended that you subclass it so that you can provide a customized UI, a way to exit back out of the view, and a method to perform whatever action you choose to on the selected GCAssets.  These are left out to allow for more flexibility to the developer.

Subclassing
-----------

It is recommended to subclass this component however there are no methods that need to be overridden when subclassing this component.  The component will work without subclassing it.

Initialization
--------------

* thumbSize (optional) - NSInteger - default value of 77
* spacingSize (optional) - NSInteger - default value of 2
* thumbCountPerRow (optional) - NSInteger - default depends on thumbSize and spacingSize.  Default is the max number of thumbs that will fit on a row.
* images (optional) - NSArray of GCAssets - defaults to all images on phone.
* selectedIndicator (optional) - UIImageView - defaults to a green checkbox on a white background in the bottom right corner of image.

Implementation
--------------

Basic - uses defaults for thumbnail appearance and loads all images on the device.

```objective-c
    GCMultiImagePicker *temp = [[GCMultiImagePicker alloc] init];
    [self presentModalViewController:temp animated:YES];
    [temp release];
```

Intermediate - customizes look of thumbnails and uses all images on the device.

```objective-c
    GCMultiImagePicker *temp = [[GCMultiImagePicker alloc] init];
    [temp setSpacingSize:20];
    [temp setThumbSize:100];
    [temp setThumbCountPerRow:2];
    [self presentModalViewController:temp animated:YES];
    [temp release];
```

Advanced - Initialized with custom appearance and GCAssets loaded from a GCChute.

```objective-c
    GCMultiImagePicker *temp = [[GCMultiImagePicker alloc] init];
    [temp setSpacingSize:20];
    [temp setThumbSize:100];
    [temp setThumbCountPerRow:2];
    [[[GCChute findById:@"34"] object] assetsInBackgroundWithCompletion:^(GCResponse *response){
        if([response isSuccessful]){
            NSArray *assets = [response object];
            [temp setImages:assets];
        }
        [self presentModalViewController:temp animated:YES];
        [temp release];
    }];
```