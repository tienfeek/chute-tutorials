//
//  UploadPicker.m
//  ChuteImageUploader
//
//  Created by Brandon Coston on 12/14/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import "UploadPicker.h"

@implementation UploadPicker
@synthesize chute;

-(void)uploadSelectedAssets{
    if([[self selectedImages] count] == 0)
        return;
    GCParcel *parcel = [GCParcel objectWithAssets:[self selectedImages] andChutes:[NSArray arrayWithObject:[self chute]]];
    [[GCUploader sharedUploader] addParcel:parcel];
    [[self navigationController] popViewControllerAnimated:YES];
}

#pragma mark - View lifecycle

- (void)viewWillAppear:(BOOL)animated
{
    [super viewDidAppear:YES];
    UIBarButtonItem *uploadButton = [[UIBarButtonItem alloc] initWithTitle:@"Upload" style:UIBarButtonItemStylePlain target:self action:@selector(uploadSelectedAssets)];
    self.navigationItem.rightBarButtonItem = uploadButton;
    [uploadButton release];
}
@end
