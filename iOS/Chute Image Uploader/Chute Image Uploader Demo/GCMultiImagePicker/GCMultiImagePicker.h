//
//  GCMultiImagePicker.h
//  ChuteSDKDevProject
//
//  Created by Brandon Coston on 9/30/11.
//  Copyright 2011 Chute Corporation. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GetChute.h"

@interface GCMultiImagePicker : GCUIBaseViewController <UITableViewDelegate, UITableViewDataSource>{
    NSArray *images;
    NSMutableSet *selected;
    IBOutlet UIImageView *selectedIndicator;
    IBOutlet UITableView *imageTable;
    NSInteger thumbSize;
    NSInteger spacingSize;
    NSInteger thumbCountPerRow;
    NSInteger initialThumbOffset;
}
//This is the size of the thumbnail displayed.  It defaults to 77 if you don't provide it.
@property (nonatomic) NSInteger thumbSize;

//This is the size of the spacing between thumbnails.  It defaults to 2 if you don't provide it.
@property (nonatomic) NSInteger spacingSize;

//This is the number of thumbnails per row.  If you don't provide this it will figure out the best fit based on the thumbSize and spacingSize.
@property (nonatomic) NSInteger thumbCountPerRow;
//This is an array of GCAssets.  If you don't provide it then the component will use your full image library from your device.
@property (nonatomic, retain) NSArray *images;

@property (nonatomic, readonly) IBOutlet UITableView *imageTable;

//this is the image overlayed on selected images.  You can either link it in Interface builder or connect it programatically.  If you don't set it then it will create a green checkbox on a white background in the lower right side of the image.
@property (nonatomic, retain) IBOutlet UIImageView *selectedIndicator;

//this will return an NSArry of the selected GCAssets.
-(NSArray*)selectedImages;

-(void)selectAllImages;
-(void)deselectAllImages;

@end
