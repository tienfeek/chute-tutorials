//
//  imagePicker.h
//  ChuteAvatars
//
//  Created by Brandon Coston on 1/1/12.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GCImageGrid.h"

@interface imagePicker : GCImageGrid

@property (nonatomic, assign) NSString *userID;
@property (nonatomic, assign) GCChute *chute;
@property (nonatomic, retain) GCAsset *asset;

@end
