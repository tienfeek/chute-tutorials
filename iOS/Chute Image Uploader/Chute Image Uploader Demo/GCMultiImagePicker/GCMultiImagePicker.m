//
//  GCMultiImagePicker.m
//  ChuteSDKDevProject
//
//  Created by Brandon Coston on 9/30/11.
//  Copyright 2011 Chute Corporation. All rights reserved.
//

#import "GCMultiImagePicker.h"

@interface GCMultiImagePicker (Private)
-(UIView*)viewForIndexPath:(NSIndexPath*)indexPath;
@end

@implementation GCMultiImagePicker
@synthesize images, selectedIndicator, imageTable;
@synthesize thumbSize, spacingSize, thumbCountPerRow;

-(void)objectTappedWithGesture:(UIGestureRecognizer*)gesture{
    UIImageView *view = (UIImageView*)[gesture view];
    GCAsset *asset = [[self images] objectAtIndex:[view tag]];
    if(![selected containsObject:asset]){
        UIImageView *v = [[UIImageView alloc] initWithImage:self.selectedIndicator.image];
        [v setBackgroundColor:[UIColor clearColor]];
        [v setFrame:CGRectMake(0, 0, view.frame.size.width, view.frame.size.height)];
        [view addSubview:v];
        [v release];
        [selected addObject:asset];
    }
    else{
        for(UIImageView *v in view.subviews){
            [v removeFromSuperview];
        }
        [selected removeObject:asset];
    }
}

-(NSArray*)selectedImages{
    return [selected allObjects];
}


-(void)selectAllImages{
    [selected unionSet:[NSSet setWithArray:images]];
    [imageTable reloadRowsAtIndexPaths:[imageTable indexPathsForVisibleRows] withRowAnimation:UITableViewRowAnimationNone];
}
-(void)deselectAllImages{
    [selected minusSet:[NSSet setWithArray:images]];
    [imageTable reloadRowsAtIndexPaths:[imageTable indexPathsForVisibleRows] withRowAnimation:UITableViewRowAnimationNone];
}

-(void)resetView{
    [selected release];
    selected = [[NSMutableSet alloc] init];
    if(images && images.count > 0)
        [imageTable scrollToRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0] atScrollPosition:UITableViewScrollPositionTop animated:NO];
    [imageTable reloadData];
    [self hideHUD];
}




- (id)init
{
    self = [super init];
    if (self) {
        [self setSpacingSize:2];
        [self setThumbSize:77];
        [self setThumbCountPerRow:-1];
    }
    return self;
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        [self setSpacingSize:2];
        [self setThumbSize:77];
        [self setThumbCountPerRow:-1];
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
    [imageTable setAllowsSelection:NO];
    [imageTable setDelegate:self];
    [imageTable setDataSource:self];
    [imageTable setBackgroundColor:[UIColor clearColor]];
    [imageTable setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    selected = [[NSMutableSet alloc] init];
    
    if([self thumbCountPerRow] <= 0){
        if([self spacingSize]+[self thumbSize] < imageTable.frame.size.width){
            initialThumbOffset = (((int)imageTable.frame.size.width+[self spacingSize])%([self thumbSize]+[self spacingSize]))/2;
            [self setThumbCountPerRow:ceil((((imageTable.frame.size.width-(2*initialThumbOffset)))/([self thumbSize]+[self spacingSize])))];
        }
        else{
            initialThumbOffset = (imageTable.frame.size.width - self.thumbSize)/2;
            [self setThumbCountPerRow:1];
        }
    }
    else{
        initialThumbOffset = ((int)imageTable.frame.size.width+[self spacingSize]-([self thumbCountPerRow]*([self thumbSize]+[self spacingSize])))/2;
    }
    if(!selectedIndicator){
        UIImageView *temp = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, self.thumbSize, self.thumbSize)];
        UIGraphicsBeginImageContext(CGSizeMake(self.thumbSize, self.thumbSize));
        [[UIColor whiteColor] setFill];
        CGContextFillRect(UIGraphicsGetCurrentContext(), CGRectMake(self.thumbSize-(self.thumbSize/4), self.thumbSize-(self.thumbSize/4), self.thumbSize/4, self.thumbSize/4));
        [[UIColor greenColor] setFill];
        [[UIColor blackColor] setStroke];
        [@"\u2713" drawInRect:CGRectMake(self.thumbSize-(self.thumbSize/4), self.thumbSize-(self.thumbSize/4), self.thumbSize/4, self.thumbSize/4) withFont:[UIFont systemFontOfSize:self.thumbSize/4]];
        UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();
        [temp setImage:image];
        [temp setBackgroundColor:[UIColor clearColor]];
        [self setSelectedIndicator:temp];
        [temp release];
    }
    if(!self.images){
        [self showHUDWithTitle:@"loading photos" andOpacity:.5];
        if([[GCAccount sharedManager] assetsArray] && [[GCAccount sharedManager] assetsArray] > 0){
            [self setImages:[[GCAccount sharedManager] assetsArray]];
            [imageTable reloadData];
            [self hideHUD];
        }
        else{
            [[GCAccount sharedManager] loadAssetsCompletionBlock:^(void){
                [self setImages:[[GCAccount sharedManager] assetsArray]];
                [imageTable reloadData];
                [self hideHUD];
            }];
        }
    }
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

-(void)dealloc{
    [selected release];
    [images release];
    [selectedIndicator release];
    [super dealloc];
}

#pragma mark UITableViewDataSource Delegate Methods

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    // Return the number of sections.
    return 1;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    // Return the number of rows in the section.
	if(!images)
		return 0;
    return ceil([images count]/((float)self.thumbCountPerRow));
}


// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
		
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^(void) {
        UIView *v = [self viewForIndexPath:indexPath];
        dispatch_async(dispatch_get_main_queue(), ^(void) {
            for(UIView *view in cell.contentView.subviews){
                [view removeFromSuperview];
            }
            [cell.contentView addSubview:v];
        });
    });
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
	
	return thumbSize+spacingSize;
}

-(UIView*)viewForIndexPath:(NSIndexPath*)indexPath{
    UIView *view = [[[UIView alloc] initWithFrame:CGRectMake(0, 0, imageTable.frame.size.width, [self tableView:imageTable heightForRowAtIndexPath:indexPath])] autorelease];
    int index = indexPath.row * self.thumbCountPerRow;
	int maxIndex = index + self.thumbCountPerRow-1;
    CGRect rect = CGRectMake(initialThumbOffset, self.spacingSize/2, self.thumbSize, self.thumbSize);
    int x = self.thumbCountPerRow;
    if (maxIndex >= [[self images] count]) {
        x = x - (maxIndex - [[self images] count]) - 1;
    }
    
    for (int i=0; i<x; i++) {
        GCAsset *asset = [[self images] objectAtIndex:index+i];
        UIImageView *image = [[[UIImageView alloc] initWithFrame:rect] autorelease];
        [image setTag:index+i];
        UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(objectTappedWithGesture:)];
        [image addGestureRecognizer:tap];
        [tap release];
        [image setUserInteractionEnabled:YES];
        if([selected containsObject:asset]){
            UIImageView *v = [[UIImageView alloc] initWithImage:self.selectedIndicator.image];
            [v setBackgroundColor:[UIColor clearColor]];
            [v setFrame:CGRectMake(0, 0, self.thumbSize, self.thumbSize)];
            [image addSubview:v];
            [v release];
        }
        if([asset alAsset]){
            dispatch_async(dispatch_get_main_queue(), ^(void) {
                [image setImage:[asset thumbnail]];
                [view addSubview:image];
            });
        }
        else{
            [image setImage:[asset thumbnail]];
            [view addSubview:image];
        }
        rect = CGRectMake((rect.origin.x+self.thumbSize+self.spacingSize), rect.origin.y, rect.size.width, rect.size.height);
    }
    return view;
}

@end
