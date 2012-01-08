//
//  GCImageGrid.h
//  ChuteSDKDevProject
//
//  Created by Brandon Coston on 10/3/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GetChute.h"

@interface GCImageGrid : GCUIBaseViewController <UITableViewDelegate, UITableViewDataSource>{
    NSArray *objects;
    IBOutlet UITableView *objectTable;
    NSInteger thumbSize;
    NSInteger spacingSize;
    NSInteger thumbCountPerRow;
    NSInteger initialThumbOffset;
}
@property (nonatomic, retain) NSArray *objects;
@property (nonatomic) NSInteger thumbSize;
@property (nonatomic) NSInteger spacingSize;
@property (nonatomic) NSInteger thumbCountPerRow;

//override in subclass to change behavior
-(void)objectTappedAtIndex:(NSInteger)index;

@end
