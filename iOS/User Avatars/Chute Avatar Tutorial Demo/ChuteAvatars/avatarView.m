//
//  avatarView.m
//  ChuteAvatars
//
//  Created by Brandon Coston on 1/2/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "avatarView.h"

@implementation avatarView
@synthesize userID, avatar;

-(void)viewDidAppear:(BOOL)animated{
    [super viewDidAppear:animated];
    [GCAsset searchMetaDataForKey:@"CAT_USER_ID" andValue:[self userID] inBackgroundWithCompletion:^(GCResponse *response){
        if([response isSuccessful]){
            NSArray *assets = [response object];
            if([assets count] > 0){
                GCAsset *asset = [assets objectAtIndex:0];
                [[self avatar] setImageWithURL:[NSURL URLWithString:[asset urlStringForImageWithWidth:100 andHeight:100]]];
            }
        }
    }];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

@end
