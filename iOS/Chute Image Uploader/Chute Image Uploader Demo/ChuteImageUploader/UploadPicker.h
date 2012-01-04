//
//  UploadPicker.h
//  ChuteImageUploader
//
//  Created by Brandon Coston on 12/14/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GCMultiImagePicker.h"

@interface UploadPicker : GCMultiImagePicker

@property (nonatomic, assign) GCChute *chute;

-(void)uploadSelectedAssets;

@end
