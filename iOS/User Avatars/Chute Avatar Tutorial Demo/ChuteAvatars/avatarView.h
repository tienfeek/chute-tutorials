//
//  avatarView.h
//  ChuteAvatars
//
//  Created by Brandon Coston on 1/2/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GetChute.h"

@interface avatarView : UIViewController

@property (nonatomic, assign) NSString *userID;
@property (nonatomic, readonly) IBOutlet UIImageView *avatar;

@end
